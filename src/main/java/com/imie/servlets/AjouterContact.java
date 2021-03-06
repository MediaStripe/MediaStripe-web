package com.imie.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.constant.VuesEnum;
import com.imie.entities.Utilisateur;
import com.imie.services.UtilisateurService;
import com.imie.util.SessionUtils;

/**
 * Servlet implementation class AjouterContact
 */
@WebServlet("/AjouterContact")
public class AjouterContact extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final String VUE = VuesEnum.LISTER_CONTACTS.val();
	
	@Inject
	private UtilisateurService utilisateurService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterContact() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionUtils.checkUtilisateurConnecte(this, request, response);
		
		final Utilisateur utilisateurAAjouter = utilisateurService.findById(Integer.parseInt(request.getParameter("utilisateur")));
		
		final Utilisateur utilisateurConnecte = SessionUtils.getUtilisateurConnecte(request);
		utilisateurConnecte.getContacts().add(utilisateurAAjouter);
		utilisateurAAjouter.getContacts().add(utilisateurConnecte);
		
		utilisateurService.update(utilisateurConnecte);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
