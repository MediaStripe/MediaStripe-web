package com.imie.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imie.contant.VuesEnum;

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
		final HttpSession session = request.getSession();
		
		/* Redirection de l'utilisateur s'il n'est pas connecté. */
		if(session.isNew()) {
			response.sendRedirect(VuesEnum.CONNEXION.val());
		}
		
		this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
	}
}
