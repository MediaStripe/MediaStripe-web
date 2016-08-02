package com.imie.business.controls;

import com.imie.business.InscriptionForm;
import com.imie.entities.Utilisateur;
import com.imie.exceptions.BusinessException;
import com.imie.util.RegexUtil;
import com.imie.util.StringUtil;

public final class UtilisateurControls {
	/** Taille minimale du mot de passe d'utilisateur. */
	private static int PWD_LENGTH_MIN = 6;

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
	public static void validationNom(final String nom)
			throws BusinessException {
		if (StringUtil.isNull(nom) || nom.length() <= 2) {
			throw new BusinessException(
					"Le nom d'utilisateur doit contenir au moins 2 caractères.");
		}
		if (StringUtil.containNumber(nom) || StringUtil.containSpecialChar(nom)) {
			throw new BusinessException(
					"Le nom d'utilisateur ne doit ni contenir de chiffres ni de caractères spéciaux.");
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
	public static void validationPrenom(final String prenom)
			throws BusinessException {
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
	public static void validationMail(final String mail)
			throws BusinessException {
		// Au cas où le navigateur ne prend pas en compte les inputs de type
		// email.
		if (StringUtil.isNull(mail) || !RegexUtil.isEmail(mail)) {
			throw new BusinessException(
					"L'adresse mail saisie est incorrecte.");
		}
	}

	/**
	 * Effectue la validation du mot de passe de l'utilisateur, puis le hashe
	 * s'il correspond aux critères de validité.
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
	public static void validationPassword(final Utilisateur utilisateur, final String password)
			throws BusinessException {
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
		
		// TODO : exporter ce traitement métier ailleurs
		// Hashage du mot de passe
		utilisateur.setMotdepasse(StringUtil.hashPassword(password));
	}
}
