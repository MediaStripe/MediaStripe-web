package com.imie.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.contant.VuesEnum;
import com.imie.services.impl.FichierService;

/**
 * Servlet implementation class ConsultationVideo
 */
@WebServlet("/ConsultationMedia")
public class ConsultationMedia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VUE = VuesEnum.CONSULTATION_MEDIA.val();

	// TODO : Corriger l'injection via @EJB
//	@EJB
	private FichierService fichierService = new FichierService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConsultationMedia() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("fichier", fichierService.findById(Integer.parseInt(request.getParameter("media"))));
		
		this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
	}

}
