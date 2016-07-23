package com.imie.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Inscription
 */
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Inscription() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/inscription.jsp") ;
	    
	     // inclusion de cette ressource
	    requestDispatcher.include(request, response) ;
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stubv
		//doGet(request, response);
		/*Utilisateur utilisateur = new Utilisateur();
		utilisateur.setNom(request.getParameter("lastName"));
		utilisateur.setPrenom(request.getParameter("firstName"));
		utilisateur.setDateinscription(new Date());
		utilisateur.setMotdepasse(request.getParameter("userPassword"));
		utilisateur.setMail(request.getParameter("pseudo"));
		System.out.println(request.getParameter("userPassword"));
		
		UtilisateurService service = new UtilisateurService();
		service.insert(utilisateur);*/
	}

}
