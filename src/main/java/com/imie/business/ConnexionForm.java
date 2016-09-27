package com.imie.business;

import static com.imie.constant.ChampsUtilisateurEnum.MAIL;
import static com.imie.constant.ChampsUtilisateurEnum.PASSWORD;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.imie.entities.Utilisateur;
import com.imie.services.UtilisateurService;
import com.imie.util.StringUtil;

public class ConnexionForm extends AbstractBusiness {
	
	private static final String RESULT = "result";
	
	
	private UtilisateurService utilisateurService;
	
	@Inject
	public void setUtilisateurService(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	public ConnexionForm() {
		super();
	}

	public void connexionUtilisateur(final HttpServletRequest request) {
		final String mailSaisi = getValeurChamp(request, MAIL.val());
		final String motDePasseSaisi = getValeurChamp(request, PASSWORD.val());
		
		Utilisateur utilisateur = null;
		try {
			utilisateur = utilisateurService.findByEmail(mailSaisi);
		} catch(final NoResultException ex) {
			setErreur(RESULT, "L'adresse mail n'est pas reconnue.");
		}
		
		if(utilisateur != null) {
			if(!StringUtil.compareHash(motDePasseSaisi, utilisateur.getMotdepasse())) {
				setErreur(RESULT, "L'addresse mail et le mot de passe saisis ne correspondent pas.");
			}
		}
		
		if(listeErreurs.isEmpty()) {
			final HttpSession session = request.getSession();
			session.setAttribute("utilisateur", utilisateur);
		} else {
			/* En cas de mauvais mot de passe, on ne retourne que l'adresse mail saisie. */
			request.setAttribute("mailSaisi", mailSaisi);
		}
		
		resultat = listeErreurs.isEmpty() ? "Authentification réussie." : "Échec de l'authentification.";
	}
}
