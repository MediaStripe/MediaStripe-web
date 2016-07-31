package com.imie.business;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import com.imie.entities.Utilisateur;
import com.imie.services.impl.UtilisateurService;

public class ConnexionForm extends AbstractBussiness {
	
	@EJB
	private UtilisateurService utilisateurService = new UtilisateurService();
	
	public Utilisateur connexionUtilisateur(final HttpServletRequest request) {
		
		return null;
	}
}
