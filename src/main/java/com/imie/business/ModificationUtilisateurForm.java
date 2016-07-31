package com.imie.business;

import static com.imie.contant.ChampsUtilisateurEnum.MAIL;
import static com.imie.contant.ChampsUtilisateurEnum.NOM;
import static com.imie.contant.ChampsUtilisateurEnum.PRENOM;

import javax.ejb.EJB;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import com.imie.business.controls.UtilisateurControls;
import com.imie.entities.Utilisateur;
import com.imie.exceptions.BusinessException;
import com.imie.services.impl.UtilisateurService;

public class ModificationUtilisateurForm extends AbstractBussiness {

	// TODO : Corriger l'injection via @EJB
	@EJB
	private UtilisateurService utilisateurService = new UtilisateurService();
	
	public void modifierUtilisateur(final HttpServletRequest request) {
		final Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
		
		final String nouveauNom = (String) request.getAttribute("nom");
		final String nouveauPrenom = (String) request.getAttribute("prenom");
		final String nouveauMail = (String) request.getAttribute("mail");
		
		if(!utilisateur.getNom().equals(nouveauNom)) {
			try {
				UtilisateurControls.validationNom(nouveauNom);
				utilisateur.setPrenom(nouveauPrenom);
			} catch (final BusinessException be) {
				setErreur(NOM.val(), be.getMessage());
			}
		}
		
		if(!utilisateur.getPrenom().equals(nouveauPrenom)) {
			try {
				UtilisateurControls.validationPrenom(nouveauPrenom);
				utilisateur.setPrenom(nouveauPrenom);
			} catch (final BusinessException be) {
				setErreur(PRENOM.val(), be.getMessage());
			}
		}
		
		if(!utilisateur.getMail().equals(nouveauMail)) {
			validationMail(utilisateur, nouveauMail);
		}
		
		resultat = listeErreurs.isEmpty() ? "Mise à jour effectuée."
				: "Échec de la mise à jour des informations personnelles.";
		
		if(listeErreurs.isEmpty()) {
			utilisateurService.update(utilisateur);
		}
	}
	
	/**
	 * Effectue le contrôle de l'adresse mail saisie et vérifie que cette dernière n'est pas utilisée par un autre utilisateur.
	 * @param mail La nouvelle adresse mail saisie.
	 */
	private void validationMail(final Utilisateur utilisateur, final String mail) {
		try {
			UtilisateurControls.validationMail(mail);
		} catch (final BusinessException be) {
			setErreur(MAIL.val(), be.getMessage());
		}
		
		try {
			utilisateurService.findByEmail(mail);
			/*
			 * Si on arrive ici, c'est que findByEmail a trouvé un utilisateur
			 * avec l'adresse passée en paramètre. Donc on ajoute une erreur car
			 * la nouvelle adresse mail n'est pas disponible.
			 */
			setErreur("result", "L'adresse mail correspond déjà à un utilisateur.");
		} catch(final NoResultException ex) {
			// On modifie l'adresse mail si elle n'est pas trouvée en base.
			utilisateur.setMail(mail);
		}
	}
}
