package com.imie.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.business.InscriptionForm;
import com.imie.entities.Utilisateur;

/**
 * Servlet implementation class Inscription
 */
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VUE_FORM = "/WEB-INF/jsp/inscriptionForm.jsp";

	private static final String VUE_SUCCESS = "/WEB-INF/jsp/inscriptionSuccess.jsp";

	private InscriptionForm inscriptionForm;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Inscription() {
		super();
		inscriptionForm = new InscriptionForm();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final Utilisateur utilisateur = inscriptionForm.creerUtilisateur(request);

		request.setAttribute("form", inscriptionForm);
		request.setAttribute("utilisateur", utilisateur);

		this.getServletContext().getRequestDispatcher(
			inscriptionForm.getListeErreurs().isEmpty() ? VUE_SUCCESS : VUE_FORM)
				.forward(request, response);
	}

}
