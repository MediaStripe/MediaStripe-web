package com.imie.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet de deconnexion, détruisant la session de l'utilisateur.
 */
@WebServlet("/Deconnexion")
public class Deconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Chaîne correspondant au mappage de la servlet dont sera redirigé
	 * l'utilisateur après la destruction de sa session en mémoire.
	 */
	private static final String VUE = "./Accueil";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deconnexion() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final HttpSession session = request.getSession();
		
		session.invalidate();
		
		response.sendRedirect(VUE);
	}

}
