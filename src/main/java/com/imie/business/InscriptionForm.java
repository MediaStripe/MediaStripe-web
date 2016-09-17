package com.imie.business;

import static com.imie.business.controls.UtilisateurControls.validationMail;
import static com.imie.business.controls.UtilisateurControls.validationNom;
import static com.imie.business.controls.UtilisateurControls.validationPassword;
import static com.imie.business.controls.UtilisateurControls.validationPrenom;
import static com.imie.contant.ChampsUtilisateurEnum.MAIL;
import static com.imie.contant.ChampsUtilisateurEnum.NOM;
import static com.imie.contant.ChampsUtilisateurEnum.PASSWORD;
import static com.imie.contant.ChampsUtilisateurEnum.PRENOM;

import java.util.Date;

import javax.ejb.EJB;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import com.imie.entities.Utilisateur;
import com.imie.exceptions.BusinessException;
import com.imie.services.impl.UtilisateurService;

/**
 * Traitements métier relatifs à l'enregistrement d'un nouvel utilisateur.
 * 
 * @author takiguchi
 *
 */
public class InscriptionForm extends AbstractBusiness {
	// TODO : Corriger l'injection via @EJB
	@EJB
	private UtilisateurService utilisateurService = new UtilisateurService();
	
	/**
	 * Procède aux contrôles métier avant d'effectuer l'enregistrement du nouvel
	 * utilisateur en base.
	 * 
	 * @param request
	 * @return
	 */
	public Utilisateur creerUtilisateur(final HttpServletRequest request) {
		final Utilisateur utilisateur = new Utilisateur();

		utilisateur.setNom(getValeurChamp(request, NOM.val()));
		utilisateur.setPrenom(getValeurChamp(request, PRENOM.val()));
		utilisateur.setMail(getValeurChamp(request, MAIL.val()));
		utilisateur.setMotdepasse(getValeurChamp(request, PASSWORD.val()));
		final String validationMotdepasse = getValeurChamp(request, "verificationMotdepasse");

		validationInscription(utilisateur, validationMotdepasse);
		
		if(listeErreurs.isEmpty()) {
			try {
				utilisateurService.findByEmail(utilisateur.getMail());
				
				setErreur("result", "L'adresse mail correspond déjà à un utilisateur.");
			} catch (final PersistenceException ex) {
				// date d'inscription = date du jour
				utilisateur.setDateinscription(new Date());
				utilisateurService.insert(utilisateur);
			}
		}
		
		resultat = listeErreurs.isEmpty() ? "Inscription réussie."
				: "Échec de l'inscription.";

		return utilisateur;
	}
	
	/**
	 * Effectue le contrôle des champs du formulaire d'inscription.
	 * 
	 * @param utilisateur
	 *            L'utilisateur créé à partir des champs du formulaire
	 *            d'inscription.
	 * @param validationMotdepasse 
	 */
	void validationInscription(final Utilisateur utilisateur, final String validationMotdepasse) {
		try {
			validationNom(utilisateur.getNom());
		} catch (final BusinessException be) {
			setErreur(NOM.val(), be.getMessage());
		}

		try {
			validationPrenom(utilisateur.getPrenom());
		} catch (final BusinessException be) {
			setErreur(PRENOM.val(), be.getMessage());
		}

		try {
			validationMail(utilisateur.getMail());
		} catch (final BusinessException be) {
			setErreur(MAIL.val(), be.getMessage());
		}

		if(utilisateur.getMotdepasse().equals(validationMotdepasse)){
			try {
				validationPassword(utilisateur, utilisateur.getMotdepasse());
			} catch (final BusinessException be) {
				setErreur(PASSWORD.val(), be.getMessage());
			}
		} else {
			setErreur(PASSWORD.val(), "Les deux mots de passe ne correspondent pas.");
		}
	}
}
