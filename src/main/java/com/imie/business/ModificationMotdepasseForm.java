package com.imie.business;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.imie.business.controls.UtilisateurControls;
import com.imie.entities.Utilisateur;
import com.imie.exceptions.BusinessException;
import com.imie.services.UtilisateurService;
import com.imie.util.SessionUtils;
import com.imie.util.StringUtil;

/**
 * Traitement métier relatif à la modification de mot de passe d'un utilisateur.
 * 
 * @author takiguchi
 *
 */
public class ModificationMotdepasseForm extends AbstractBusiness {

	private static final String ATT_MOTDEPASSE_ACTUEL = "actuelMotdepasse";
	
	private static final String ATT_NOUVEAU_MOTDEPASSE = "nouveauMotdepasse";
	
	private static final String ATT_VERIF_MOTDEPASSE = "verificationMotdepasse";
	
	@Inject
	private UtilisateurService utilisateurService;
	
	/**
	 * Effectue les contrôles de saisie puis effectue la modification du mot de
	 * passe de l'utilisateur s'il n'y a pas eu d'erreur lors des contrôles.
	 * 
	 * @param request
	 */
	public void modifierMotdepasse(final HttpServletRequest request) {
		reinit();
		
		final Utilisateur utilisateur = SessionUtils.getUtilisateurConnecte(request);
		
		final String motdepasseActuel = getValeurChamp(request, ATT_MOTDEPASSE_ACTUEL);
		final String nouveauMotdepasse = getValeurChamp(request, ATT_NOUVEAU_MOTDEPASSE);
		final String verificationMotdepasse = getValeurChamp(request, ATT_VERIF_MOTDEPASSE);
		
		validationMotdepasseActuel(utilisateur, motdepasseActuel);
		
		validationNouveauMotdepasse(utilisateur, nouveauMotdepasse, verificationMotdepasse);
		
		resultat = listeErreurs.isEmpty() ? "Mise à jour effectuée."
				: "Échec de la mise à jour du mot de passe.";
		
		if(listeErreurs.isEmpty()) {
			utilisateur.setMotdepasse(StringUtil.hashPassword(nouveauMotdepasse));
			utilisateurService.update(utilisateur);
		}
	}

	/**
	 * Vérifie que le mot de passe saisi correspond au mot de passe actuel de
	 * l'utilisateur.
	 * 
	 * @param utilisateur
	 *            L'utilisateur connecté.
	 * @param motdepasseActuel
	 *            Le mot de passe saisi via le champ "{@code Mot de passe actuel}
	 *            " du formulaire.
	 */
	private void validationMotdepasseActuel(final Utilisateur utilisateur,
			final String motdepasseActuel) {
		if(!StringUtil.compareHash(motdepasseActuel, utilisateur.getMotdepasse())) {
			setErreur(ATT_MOTDEPASSE_ACTUEL, "Le mot de passe saisi est incorrect.");
		}
	}
	
	/**
	 * Vérifie que le nouveau mot de passe est identique à la vérification du
	 * nouveau mot de passe.
	 * 
	 * @param nouveauMotdepasse
	 *            Le mot de passe saisi via le champ "
	 *            {@code Nouveau mot de passe} " du formulaire.
	 * @param verificationMotdepasse
	 *            Le mot de passe saisi via le champ "
	 *            {@code Vérification nouveau mot de passe} " du formulaire.
	 */
	private void validationNouveauMotdepasse(final Utilisateur utilisateur, final String nouveauMotdepasse,
			final String verificationMotdepasse) {
		if(nouveauMotdepasse.equals(verificationMotdepasse)) {
			try {
				UtilisateurControls.validationPassword(utilisateur, nouveauMotdepasse);
			} catch (final BusinessException e) {
				setErreur(ATT_NOUVEAU_MOTDEPASSE, e.getMessage());
			}
		} else {
			setErreur(ATT_NOUVEAU_MOTDEPASSE, "Les deux mots de passe ne correspondent pas.");
		}
	}
}
