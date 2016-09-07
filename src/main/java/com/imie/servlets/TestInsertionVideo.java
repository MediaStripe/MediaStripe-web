package com.imie.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.entities.Fichier;
import com.imie.entities.Tag;
import com.imie.entities.Utilisateur;
import com.imie.entities.Video;
import com.imie.services.impl.FichierService;
import com.imie.services.impl.TagService;
import com.imie.services.impl.UtilisateurService;

/**
 * Servlet implementation class TestInsertionVideo
 */
@WebServlet("/TestInsertionVideo")
public class TestInsertionVideo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private FichierService fichierService = new FichierService();
	private UtilisateurService utilisateurService = new UtilisateurService();
	private TagService tagService = new TagService();

	private Utilisateur user;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestInsertionVideo() {
		super();
		user = utilisateurService.findByEmail("florian.thierry72@gmail.com");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result = "";

		try {
			final Fichier fichier = new Video();

			// Affectation des valeurs du formulaire au fichier
			fichier.setTitre("Titre");
			fichier.setDescription("Description");
			fichier.setPublique(true);
			fichier.setMotsClefs("Test;Upload;Fichiers");

			fichier.setDatecreation(new Timestamp(new Date().getTime()));
			fichier.setCheminfichier("CheminDuFichier");

			fichier.setPublieur(utilisateurService.findByEmail(user.getMail()));
			
			fichierService.insert(fichier);
					
			// Construction du thème principal et des mots clefs du fichier
			// (peut etre fait après les controles)
			alimenterTags(fichier, "Test");
			
			fichierService.update(fichier);
		} catch (Exception ex) {
			result = ex.getMessage() + ex.getStackTrace();
			ex.printStackTrace();
		}

		request.setAttribute("retour", result);

		getServletContext().getRequestDispatcher("/WEB-INF/jsp/testInsertionVideo.jsp").forward(request, response);
	}
	
	
	private void alimenterTags(final Fichier fichier, final String mainTheme) {
		fichier.setMainTheme(getTag(mainTheme));

		List<Tag> listeTags = new ArrayList<Tag>();

		for (String libelle : fichier.getMotsClefs().split(";")) {
			fichier.addTag(getTag(libelle));
		}

		fichier.setListeTags(listeTags);
	}

	/**
	 * Retourne le tag correspondant au libellé passé en paramètre. S'il
	 * n'existe pas en base, il sera créé avant d'être retourné.
	 * 
	 * @param libelle
	 *            Le libellé du mot clef.
	 */
	private Tag getTag(final String libelle) {
		final String libelleTimed = libelle.trim();
		System.out.println("Récupération du tag \"" + libelleTimed + "\"");
		Tag tag = tagService.findByLibelle(libelleTimed);

		if (tag == null) {
			System.out.println("Tag non trouvé");
			tag = new Tag(libelleTimed);
			tagService.insert(tag);
			System.out.println("Tag inséré : " + tag.toString());
		}

		System.out.println("Tag retourné : " + tag.toString());
		return tag;
	}

}
