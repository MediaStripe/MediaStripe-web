package com.imie.business;

import static com.imie.contant.ChampsUtilisateurEnum.MAIL;
import static com.imie.contant.ChampsUtilisateurEnum.NOM;
import static com.imie.contant.ChampsUtilisateurEnum.PASSWORD;
import static com.imie.contant.ChampsUtilisateurEnum.PRENOM;

import java.util.Date;

import javax.ejb.EJB;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import com.imie.entities.Utilisateur;
import com.imie.exceptions.BusinessException;
import com.imie.services.impl.UtilisateurService;
import com.imie.util.RegexUtil;
import com.imie.util.StringUtil;

/**
 * Traitements métier relatifs à l'enregistrement d'un nouvel utilisateur.
 * 
 * @author takiguchi
 *
 */
public class InscriptionForm extends AbstractBussiness {
	/** Taille minimale du mot de passe d'utilisateur. */
	private static int PWD_LENGTH_MIN = 6;

	// TODO : Corriger l'injection via @EJB
	@EJB
	private UtilisateurService utilisateurService = new UtilisateurService();
	
	/**
	 * Procède aux contrôles métier avant d'effectuer l'enregistrement du nouvel utilisateur en base.
	 * @param request
	 * @return
	 */
	public Utilisateur creerUtilisateur(final HttpServletRequest request) {
		final Utilisateur utilisateur = new Utilisateur();

		utilisateur.setNom(getValeurChamp(request, NOM.val()));
		utilisateur.setPrenom(getValeurChamp(request, PRENOM.val()));
		utilisateur.setMail(getValeurChamp(request, MAIL.val()));
		utilisateur.setMotdepasse(getValeurChamp(request, PASSWORD.val()));

		validationInscription(utilisateur);
		
		if(listeErreurs.isEmpty()) {
			boolean utilisateurExistant = false;
			try {
				utilisateurService.findByEmail(utilisateur.getMail());
			} catch (final NoResultException ex) {

			} catch (final PersistenceException ex) {
				utilisateurExistant = true;
			}
			
			if(utilisateurExistant) {
				setErreur("result", "L'adresse mail correspond déjà à un utilisateur.");
			} else {
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
	 * @param utilisateur L'utilisateur créé à partir des champs du formulaire d'inscription.
	 */
	private void validationInscription(final Utilisateur utilisateur) {
		try {
			validationNom(utilisateur);
		} catch (final BusinessException be) {
			setErreur(NOM.val(), be.getMessage());
		}

		try {
			validationPrenom(utilisateur);
		} catch (final BusinessException be) {
			setErreur(PRENOM.val(), be.getMessage());
		}

		try {
			validationMail(utilisateur);
		} catch (final BusinessException be) {
			setErreur(MAIL.val(), be.getMessage());
		}

		try {
			validationPassword(utilisateur);
		} catch (final BusinessException be) {
			setErreur(PASSWORD.val(), be.getMessage());
		}
	}

	/**
	 * Effectue la validation du nom de l'utilisateur.
	 * 
	 * @param utilisateur
	 *            L'utilisateur créé à partir des champs du formulaire
	 *            d'inscription.
	 * @throws BusinessException
	 *             Si le nom est null, vide, ne contient que des espaces, s'il
	 *             fait 2 caractères ou moins, s'il contient des nombres ou des
	 *             caractères spéciaux.
	 */
	private void validationNom(final Utilisateur utilisateur)
			throws BusinessException {
		final String nom = utilisateur.getNom();
		if (StringUtil.isNull(nom) || nom.length() <= 2) {
			throw new BusinessException(
					"Le nom d'utilisateur doit contenir au moins 2 caractères.");
		}
		if (StringUtil.containNumber(nom) || StringUtil.containSpecialChar(nom)) {
			throw new BusinessException(
					"Le prénom d'utilisateur ne doit ni contenir de chiffres ni de caractères spéciaux.");
		}
	}

	/**
	 * Effectue la validation du prénom de l'utilisateur.
	 * 
	 * @param utilisateur
	 *            L'utilisateur créé à partir des champs du formulaire
	 *            d'inscription.
	 * @throws BusinessException
	 *             Si le prénom est null, vide, ne contient que des espaces,
	 *             s'il fait 2 caractères ou moins, s'il contient des nombres ou
	 *             des caractères spéciaux.
	 */
	private void validationPrenom(final Utilisateur utilisateur)
			throws BusinessException {
		final String prenom = utilisateur.getPrenom();
		if (StringUtil.isNull(prenom) || prenom.length() <= 2) {
			throw new BusinessException(
					"Le prénom d'utilisateur doit contenir au moins 2 caractères.");
		}
		if (StringUtil.containNumber(prenom)
				|| StringUtil.containSpecialChar(prenom)) {
			throw new BusinessException(
					"Le prénom d'utilisateur ne doit ni contenir de chiffres ni de caractères spéciaux.");
		}
	}

	/**
	 * Effectue la validation de l'adresse mail de l'utilisateur.
	 * 
	 * @param utilisateur
	 *            L'utilisateur créé à partir des champs du formulaire
	 *            d'inscription.
	 * @throws BusinessException
	 *             Si l'adresse mail est null, vide, ne contient que des espaces
	 *             ou si elle ne correspond pas au pattern d'une adresse mail.
	 */
	private void validationMail(final Utilisateur utilisateur)
			throws BusinessException {
		final String mail = utilisateur.getMail();
		// Au cas où le navigateur ne prend pas en compte les inputs de type
		// email.
		if (StringUtil.isNull(mail) || !RegexUtil.isEmail(mail)) {
			throw new BusinessException(
					"Le prénom d'utilisateur doit contenir au moins 2 caractères.");
		}
	}

	/**
	 * Effectue la validation du mot de passe de l'utilisateur.
	 * 
	 * @param utilisateur
	 *            L'utilisateur créé à partir des champs du formulaire
	 *            d'inscription.
	 * @throws BusinessException
	 *             Si le mot de passe fait moins de caractères que la taille
	 *             minimale spécifiée via la constante
	 *             {@link InscriptionForm#PWD_LENGTH_MIN}, ou si le mot de passe
	 *             ne contient pas 3 des conditions suivantes :<br/>
	 *             <ul>
	 *             <li>Contenir des les minuscules</li>
	 *             <li>Contenir des lettres majuscules</li>
	 *             <li>Contenir des chiffres</li>
	 *             <li>Contenir des caractères spéciaux</li>
	 *             </ul>
	 */
	// Portée package pour pouvoir tester la fonction avec des tests unitaires.
	void validationPassword(final Utilisateur utilisateur)
			throws BusinessException {
		final String password = utilisateur.getMotdepasse();

		if (password.length() < PWD_LENGTH_MIN) {
			throw new BusinessException(
					"Le mot de passe utilisateur doit faire au moins " + PWD_LENGTH_MIN + " caractères.");
		}

		int score = 0;

		if (StringUtil.containLowercase(password)) {
			score++;
		}
		if (StringUtil.containUppercase(password)) {
			score++;
		}
		if (StringUtil.containNumber(password)) {
			score++;
		}
		if (StringUtil.containSpecialChar(password)) {
			score++;
		}

		if (score < 3) {
			throw new BusinessException(
					"Le mot de passe utilisateur doit contenir 3 des 4 choix suivants : minuscule, MAJUSCULE, nombre, caractère spécial");
		}
	}
}
