package com.imie.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.business.RechercherForm;
import com.imie.contant.VuesEnum;

/**
 * Servlet implementation class Rechercher
 */
@WebServlet("/Rechercher")
public class Rechercher extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final String VUE = VuesEnum.RECHERCHER.val();
	
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
		rechercherForm = new RechercherForm();
		
		request.setAttribute("resultats", rechercherForm.rechercher(request));
		
		this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
	}

}