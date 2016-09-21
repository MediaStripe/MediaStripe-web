package com.imie.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.contant.VuesEnum;
import com.imie.services.impl.MediaService;

/**
 * Servlet implementation class Accueil
 */
@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/** Page jsp associée à la servlet. */
	private static final String VUE = VuesEnum.ACCUEIL.val();
	
	// TODO : Corriger l'injection d'EJB.
//	@EJB
	private MediaService mediaService = new MediaService();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Accueil() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listeMedia", mediaService.getDerniersPublies());
		
		this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
	}

}
