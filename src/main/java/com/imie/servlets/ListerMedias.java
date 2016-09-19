package com.imie.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.contant.VuesEnum;
import com.imie.util.SessionUtils;

/**
 * Servlet implementation class ConsultationMedias
 */
@WebServlet("/ListerMedias")
public class ListerMedias extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String VUE = VuesEnum.LISTER_MEDIAS.val();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListerMedias() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionUtils.checkUtilisateurConnecte(this, request, response);
		
		this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
	}
}
