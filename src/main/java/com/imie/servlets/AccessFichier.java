package com.imie.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.entities.Fichier;
import com.imie.entities.Utilisateur;
import com.imie.services.FichierService;
import com.imie.util.RegexUtil;

/**
 * Servlet implementation class AccessFichier
 */
@WebServlet(urlPatterns = "/fichier/*", initParams = {
	@WebInitParam(name = "repertoire", value = "/opt/MediaStripe/files/") 
})
public class AccessFichier extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int TAILLE_TAMPON = 10240; // 10ko

	private String repertoire;

	@Inject
	private FichierService fichierService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccessFichier() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (repertoire == null) {
			repertoire = getServletConfig().getInitParameter("repertoire");
		}

		/*
		 * Récupération du chemin du fichier demandé au sein de l'URL de la
		 * requête
		 */
		String fichierRequis = request.getPathInfo();

		/* Vérifie qu'un fichier a bien été fourni */
		if (fichierRequis == null) {
			/*
			 * Si non, alors on envoie une erreur 404, qui signifie que la
			 * ressource demandée n'existe pas
			 */
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		/*
		 * Décode le nom de fichier récupéré, susceptible de contenir des
		 * espaces et autres caractères spéciaux, et prépare l'objet File
		 */
		fichierRequis = URLDecoder.decode(fichierRequis, "UTF-8");

		File fichier = new File(repertoire, fichierRequis);

		/* Vérifie que le fichier existe bien */
		if (!fichier.exists()) {
			/*
			 * Si non, alors on envoie une erreur 404, qui signifie que la
			 * ressource demandée n'existe pas
			 */
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// Contrôle des droits d'accès du fichier.
		if (!controlerDroits(request, response, fichierRequis)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		// Création de la réponse.
		creerReponse(response, fichier);

		// Envoie du fichier via le buffer.
		envoyerFichier(response, fichier);
	}

	/**
	 * Construit la réponse HTTP qui sera envoyée à l'utilisateur.
	 * 
	 * @param response
	 *            La réponse HTTP.
	 * @param fichier
	 *            Le fichier à envoyer.
	 */
	private void creerReponse(final HttpServletResponse response, final File fichier) {
		/* Récupère le type du fichier */
		String type = getServletContext().getMimeType(fichier.getName());

		/*
		 * Si le type de fichier est inconnu, alors on initialise un type par
		 * défaut
		 */
		if (type == null) {
			type = "application/octet-stream";
		}

		response.reset();
		response.setBufferSize(TAILLE_TAMPON);
		response.setContentType(type);
		response.setHeader("Content-Length", String.valueOf(fichier.length()));
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fichier.getName() + "\"");
	}

	/**
	 * Envoie le fichier demandé via un buffer.
	 * 
	 * @param response
	 *            La réponse HTTP.
	 * @param fichier
	 *            Le fichier à envoyer.
	 * @throws IOException
	 */
	private void envoyerFichier(final HttpServletResponse response, final File fichier) throws IOException {
		/* Prépare les flux */
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;
		try {
			/* Ouvre les flux */
			entree = new BufferedInputStream(new FileInputStream(fichier), TAILLE_TAMPON);
			sortie = new BufferedOutputStream(response.getOutputStream(), TAILLE_TAMPON);

			/* Lit le fichier et écrit son contenu dans la réponse HTTP */
			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur;
			while ((longueur = entree.read(tampon)) > 0) {
				sortie.write(tampon, 0, longueur);
			}
		} finally {
			sortie.close();
			entree.close();
		}
	}

	/**
	 * Indique si l'utilisateur qui tente d'accéder au fichier dispose des
	 * droits nécessaires.
	 * 
	 * @param request
	 *            La requête HTTP.
	 * @param response
	 *            La réponse HTTP.
	 * @param fichierRequis
	 *            Le chemin du fichier demandé.
	 * @return {@code true} si l'utilisateur dispose des droits, {@code false}
	 *         sinon.
	 */
	private boolean controlerDroits(final HttpServletRequest request, final HttpServletResponse response,
			final String fichierRequis) {
		final Fichier fichierDemande = fichierService.findByPath(RegexUtil.getGroup("^.*\\/(.*)$", 1, fichierRequis));

		return fichierDemande.isPublique() || fichierDemande.getPublieur().equals((Utilisateur) request.getSession().getAttribute("utilisateur"));
	}
}
