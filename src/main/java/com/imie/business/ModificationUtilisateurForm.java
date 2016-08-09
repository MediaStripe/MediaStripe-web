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

/**
 * Traitement métier relatif à la modification d'informations personnelles d'un
 * utilisateur.
 * 
 * @author takiguchi
 *
 */
public class ModificationUtilisateurForm extends AbstractBusiness {

	// TODO : Corriger l'injection via @EJB
	@EJB
	private UtilisateurService utilisateurService = new UtilisateurService();
	
	/**
	 * Effectue les contrôles de saisie puis effectue la modification des
	 * informations personnelles de l'utilisateur s'il n'y a pas eu d'erreur
	 * lors des contrôles.
	 * 
	 * @param request
	 */
	public void modifierUtilisateur(final HttpServletRequest request) {
		final Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
		
		final String nouveauNom = getValeurChamp(request, NOM.val());
		final String nouveauPrenom = getValeurChamp(request, PRENOM.val());
		final String nouveauMail = getValeurChamp(request, MAIL.val());
		
		if(!utilisateur.getNom().equals(nouveauNom)) {
			try {
				UtilisateurControls.validationNom(nouveauNom);
			} catch (final BusinessException be) {
				setErreur(NOM.val(), be.getMessage());
			}
		}
		
		if(!utilisateur.getPrenom().equals(nouveauPrenom)) {
			try {
				UtilisateurControls.validationPrenom(nouveauPrenom);
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
			utilisateur.setNom(nouveauNom);
			utilisateur.setPrenom(nouveauPrenom);
			utilisateur.setMail(nouveauMail);
			utilisateurService.update(utilisateur);
		}
	}
	
	/**
	 * Effectue le contrôle de l'adresse mail saisie et vérifie que cette
	 * dernière n'est pas utilisée par un autre utilisateur.
	 * 
	 * @param mail
	 *            La nouvelle adresse mail saisie.
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
		}
	}
}
