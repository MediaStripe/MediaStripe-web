package com.imie.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.business.ConsultationMediaForm;
import com.imie.contant.VuesEnum;

/**
 * Servlet implementation class ConsultationVideo
 */
@WebServlet("/ConsultationMedia")
public class ConsultationMedia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VUE = VuesEnum.CONSULTATION_MEDIA.val();

	private ConsultationMediaForm consultationMediaForm;
	
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
		consultationMediaForm = new ConsultationMediaForm();
		
		request.setAttribute("fichier", consultationMediaForm.getFichier(request));
		request.setAttribute("form", consultationMediaForm);
		
		this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
	}
}
