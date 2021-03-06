package com.imie.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.business.ConnexionForm;
import com.imie.constant.VuesEnum;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE_FORM = VuesEnum.CONNEXION.val();
	
	private static final String VUE_ACCUEIL = VuesEnum.GESTION_COMPTE.val();
    
	@Inject
	private ConnexionForm connexionForm;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Connexion() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("accessDenied") != null){
			request.setAttribute("accessDenied", "Vous devez être connecté pour accéder à cette page.");
		}
		
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		connexionForm.connexionUtilisateur(request);
		
		if(connexionForm.getListeErreurs().isEmpty()) {
			this.getServletContext().getRequestDispatcher(VUE_ACCUEIL).forward(request, response);
		} else {
			request.setAttribute("form", connexionForm);

			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}
	}

}
