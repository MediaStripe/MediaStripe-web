package com.imie.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.entities.Fichier;
import com.imie.entities.Tag;
import com.imie.entities.Utilisateur;
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

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestInsertionVideo() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result = "";

		try {
			// // Création du media
			// Fichier fichier = new Fichier();
			//
			// fichier.setTitre("Test");
			// fichier.setDescription("Test");
			// fichier.setDatecreation(new Timestamp(new Date().getTime()));
			// fichier.setPublique(true);
			//
			// // Persistence du média
			// fichierService.insert(fichier);
			//
			// // Récupération du user
			// Utilisateur user =
			// utilisateurService.findByEmail("florian.thierry72@gmail.com");
			// fichier.setPublieur(user);
			//
			// // Affectation des tags
			// for (String libelle : new String[] { "Test", "Toto", "Titi",
			// "Tata" }) {
			// Tag tag = tagService.findByLibelle(libelle);
			//
			// if (tag == null) {
			// tag = new Tag(libelle);
			// }
			//
			// fichier.addTag(tag);
			// }
			//
			// fichierService.update(fichier);

			// Création du media
			Fichier fichier = new Fichier();

			fichier.setTitre("Test");
			fichier.setDescription("Test");
			fichier.setDatecreation(new Timestamp(new Date().getTime()));
			fichier.setPublique(true);

			// Récupération du user
			Utilisateur user = utilisateurService.findByEmail("florian.thierry72@gmail.com");
			fichier.setPublieur(user);

			System.out.println("Récupération du tag 'Test'");
			Tag mainTag = tagService.findByLibelle("Test");

			if (mainTag == null) {
				System.out.println("Tag non trouvé");
				mainTag = new Tag("Test");
				tagService.insert(mainTag);
				System.out.println("Tag inséré");
			}

			fichier.setMainTheme(mainTag);
			
			// Affectation des tags
			for (String libelle : new String[] { "Test", "Toto", "Titi", "Tata" }) {
				System.out.println("Récupération du tag '" + libelle + "'");
				Tag tag = tagService.findByLibelle(libelle);

				if (tag == null) {
					System.out.println("Tag non trouvé");
					tag = new Tag(libelle);
					tagService.insert(mainTag);
					System.out.println("Tag inséré");
				}

				fichier.addTag(tag);
			}

			// Persistence du média
			fichierService.update(fichier);

			result = "Insertion réussie !";
		} catch (Exception ex) {
			result = ex.getMessage() + ex.getStackTrace();
			ex.printStackTrace();
		}

		request.setAttribute("retour", result);

		getServletContext().getRequestDispatcher("/WEB-INF/jsp/testInsertionVideo.jsp").forward(request, response);
	}

}
