package com.imie.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.business.ModificationMotdepasseForm;
import com.imie.constant.VuesEnum;
import com.imie.util.SessionUtils;

/**
 * Servlet implementation class ModificationMotdepasse
 */
@WebServlet("/ModificationMotdepasse")
public class ModificationMotdepasse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String VUE = VuesEnum.MODIF_MOTDEPASSE.val();

	@Inject
	private ModificationMotdepasseForm modificationMotdepasseForm;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificationMotdepasse() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionUtils.checkUtilisateurConnecte(this, request, response);
		
		this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionUtils.checkUtilisateurConnecte(this, request, response);

		request.setCharacterEncoding("UTF-8");
		
		modificationMotdepasseForm.modifierMotdepasse(request);
		
		request.setAttribute("form", modificationMotdepasseForm);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
