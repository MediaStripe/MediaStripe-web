package com.imie.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.constant.VuesEnum;
import com.imie.services.UtilisateurService;

/**
 * Servlet implementation class ConsultationProfil
 */
@WebServlet("/ConsultationProfil")
public class ConsultationProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE = VuesEnum.CONSULTATION_PROFIL.val();

	@Inject
	private UtilisateurService utilisateurService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConsultationProfil() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("utilisateurConsulte",
				utilisateurService.findById(Integer.parseInt(request.getParameter("utilisateur"))));

		this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
	}

}
