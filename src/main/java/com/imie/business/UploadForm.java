package com.imie.business;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.mindrot.jbcrypt.BCrypt;

import com.imie.services.impl.FichierService;
import com.imie.util.RegexUtil;
import com.imie.util.StringUtil;

public class UploadForm extends AbstractBusiness {
	/** Taille du tampon pour le transfer du fichier depuis le client. */
	private static final int TAILLE_TAMPON = 10240;

	private static final String REGEX_CARACTERES_INTERDITS = "[\\.\\\\\\/]";

	// TODO : Corriger l'injection via @EJB
	@EJB
	private FichierService fichierService;

	public void ajouterFichier(final HttpServletRequest request) {

		Part part = null;
		try {
			part = request.getPart("fichier");
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (final ServletException ex) {
			ex.printStackTrace();
		}

		if (part != null) {
			final String typeFichier = request.getParameter("typeFichier");
			final String titre = request.getParameter("titre");
			final String description = request.getParameter("description");
			final String nomFichier = part.getSubmittedFileName();
			final String extension = nomFichier.substring(nomFichier.lastIndexOf(".") + 1);

			try {
				ecrireFichier(part, genererNomFichier(titre).concat(".").concat(extension),
						"/opt/MediaStripe/fichiers/tmp/");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
	
	/**
	 * 
	 * @param nomAttribut
	 * @param part
	 * @return
	 */
	private String getAttribut(final String nomAttribut, final Part part) {
		String result = null;

		for (final String contentDispositon : part.getHeader(
				"content-disposition").split(";")) {
			if (contentDispositon.trim().startsWith(nomAttribut)) {
				result = contentDispositon.substring(contentDispositon
						.indexOf('=') + 1);
			}
		}
		

		return result;
	}

	private void ecrireFichier(final Part part, final String nomFichier,
			final String chemin) throws IOException {
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;

		try {
			entree = new BufferedInputStream(part.getInputStream(),
					TAILLE_TAMPON);
			sortie = new BufferedOutputStream(new FileOutputStream(new File(
					new StringBuilder(chemin).append(nomFichier).toString())),
					TAILLE_TAMPON);

			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur;
			while ((longueur = entree.read(tampon)) > 0) {
				sortie.write(tampon, 0, longueur);
			}
		} finally {
			try {
				sortie.close();
			} catch (final IOException e) {

			}
			try {
				entree.close();
			} catch (final IOException e) {

			}
		}
	}

	/**
	 * Génère un nom de fichier unique en hashant le titre du fichier.
	 * 
	 * @param titreFichier
	 *            Le titre du fichier.
	 * @return Le nom du fichier généré.
	 */
	private String genererNomFichier(final String titreFichier) {
		final String hash = StringUtil.hashString(titreFichier, 4).substring(7);
		
		// Remplace les points par des tirets
		final String hashSansPoints = RegexUtil.replaceSequence(hash, "\\.", "-");
		// Remplace les slashs et les back-slashs par des a
		return RegexUtil.replaceSequence(hash, "\\\\\\/", "a");
	}
}
