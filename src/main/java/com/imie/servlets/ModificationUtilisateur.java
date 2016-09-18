package com.imie.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.business.ModificationUtilisateurForm;
import com.imie.contant.VuesEnum;
import com.imie.util.SessionUtils;

/**
 * Servlet implementation class ModificationUtilisateur
 */
@WebServlet("/ModificationUtilisateur")
public class ModificationUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE_FORM = VuesEnum.MODIF_UTILISATEUR.val();
	
	private static final String VUE_SUCCESS = VuesEnum.MODIF_UTILISATEUR.val();

	private ModificationUtilisateurForm modificationUtilisateurForm;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificationUtilisateur() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionUtils.checkUtilisateurConnecte(this, request, response);
		
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionUtils.checkUtilisateurConnecte(this, request, response);

		request.setCharacterEncoding("UTF-8");
		
		/*
		 * Réinitialisation des valeurs en mémoire au cas où il aurait eû des
		 * erreurs au précédent passage
		 */
		modificationUtilisateurForm = new ModificationUtilisateurForm();
		
		modificationUtilisateurForm.modifierUtilisateur(request);

		request.setAttribute("form", modificationUtilisateurForm);
		
		this.getServletContext().getRequestDispatcher(
				modificationUtilisateurForm.getListeErreurs().isEmpty() ? VUE_SUCCESS : VUE_FORM)
				.forward(request, response);
	}

}
