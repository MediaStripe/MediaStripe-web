package com.imie.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.business.UploadForm;
import com.imie.contant.VuesEnum;
import com.imie.entities.Fichier;
import com.imie.util.SessionUtils;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/Upload")
@MultipartConfig(location = "/opt/MediaStripe/files/tmp/", maxFileSize = 10485760L, maxRequestSize = 52428800L, fileSizeThreshold = 1048576)
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VUE_FORM = VuesEnum.UPLOAD.val();

	private static final String VUE_SUCCESS = VuesEnum.ACCUEIL.val();

	private UploadForm uploadForm;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Upload() {
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
		uploadForm = new UploadForm();

		final Fichier fichier = uploadForm.ajouterFichier(request);

		request.setAttribute("form", uploadForm);
		if (!uploadForm.getListeErreurs().isEmpty()) {
			request.setAttribute("fichier", fichier);
		}

		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

}
