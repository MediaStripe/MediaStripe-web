package com.imie.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.business.RechercherForm;
import com.imie.constant.VuesEnum;

/**
 * Servlet implementation class Rechercher
 */
@WebServlet("/Rechercher")
public class Rechercher extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final String PARAM_CRITERES = "criteres";
	
	private static final String VUE = VuesEnum.RECHERCHER.val();

	@Inject
	private RechercherForm rechercherForm;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Rechercher() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		request.setAttribute(PARAM_CRITERES, request.getParameter(PARAM_CRITERES));
		request.setAttribute("resultats", rechercherForm.rechercher(request));

		this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
	}

}
