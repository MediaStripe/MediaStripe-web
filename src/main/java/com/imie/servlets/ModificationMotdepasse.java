package com.imie.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imie.business.ModificationMotdepasseForm;
import com.imie.contant.VuesEnum;
import com.imie.util.SessionUtils;

/**
 * Servlet implementation class ModificationMotdepasse
 */
@WebServlet("/ModificationMotdepasse")
public class ModificationMotdepasse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String VUE = VuesEnum.MODIF_MOTDEPASSE.val();
	
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
		
		/*
		 * Réinitialisation des valeurs en mémoire au cas où il aurait eû des
		 * erreurs au précédent passage
		 */
		modificationMotdepasseForm = new ModificationMotdepasseForm();
		
		modificationMotdepasseForm.modifierMotdepasse(request);
		
		request.setAttribute("form", modificationMotdepasseForm);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
