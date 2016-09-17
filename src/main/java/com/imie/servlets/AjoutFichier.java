package com.imie.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.business.AjoutFichierForm;
import com.imie.contant.VuesEnum;
import com.imie.entities.Fichier;
import com.imie.util.SessionUtils;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/AjoutFichier")
@MultipartConfig(location = "/opt/MediaStripe/files/tmp/", maxFileSize = 10485760L, maxRequestSize = 52428800L, fileSizeThreshold = 1048576)
public class AjoutFichier extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VUE_FORM = VuesEnum.AJOUT_FICHIER.val();

	private AjoutFichierForm ajoutFichierForm;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjoutFichier() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionUtils.checkUtilisateurConnecte(this, request, response);

		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionUtils.checkUtilisateurConnecte(this, request, response);

		/*
		 * Réinitialisation des valeurs en mémoire au cas où il aurait eû des
		 * erreurs au précédent passage
		 */
		ajoutFichierForm = new AjoutFichierForm();

		final Fichier fichier = ajoutFichierForm.ajouterFichier(request);

		request.setAttribute("form", ajoutFichierForm);
		if (!ajoutFichierForm.getListeErreurs().isEmpty()) {
			request.setAttribute("fichier", fichier);
		}

		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

}
