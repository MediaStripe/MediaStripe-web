package com.imie.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Méthodes génériques relatives au traitement de chaînes de caractères.
 * 
 * @author takiguchi
 *
 */
public final class StringUtil {

	/**
	 * Indique si la chaîne est nulle ou si elle est vide, c'est à dire composée
	 * que d'espaces.
	 * 
	 * @param chaine
	 *            La chaîne à tester.
	 * @return {@code true} si la chaîne est nulle ou vide, {@code false} sinon.
	 */
	public static boolean isNull(final String chaine) {
		return chaine == null || chaine.trim().length() == 0;
	}

	/**
	 * Indique si la chaîne passée en paramètre contient au moins une lettre
	 * minuscule.
	 * 
	 * @param chaine
	 *            La chaîne à contrôler.
	 * @return {@code true} si la chaîne contient au moins une lettre minuscule,
	 *         {@code false} sinon.
	 */
	public static boolean containLowercase(final String chaine) {
		return RegexUtil.LETTRE_MIN_PATTERN.matcher(chaine).find();
	}

	/**
	 * Indique si la chaîne passée en paramètre contient au moins une lettre
	 * majuscule.
	 * 
	 * @param chaine
	 *            La chaîne à contrôler.
	 * @return {@code true} si la chaîne contient au moins une lettre majuscule,
	 *         {@code false} sinon.
	 */
	public static boolean containUppercase(final String chaine) {
		return RegexUtil.LETTRE_MAJ_PATTERN.matcher(chaine).find();
	}

	/**
	 * Indique si la chaîne passée en paramètre contient au moins un chiffre.
	 * 
	 * @param chaine
	 *            La chaîne à contrôler.
	 * @return {@code true} si la chaîne contient au moins un chiffre,
	 *         {@code false} sinon.
	 */
	public static boolean containNumber(final String chaine) {
		return RegexUtil.NUMBER_PATTERN.matcher(chaine).find();
	}

	/**
	 * Indique si la chaîne passée en paramètre contient au moins un chiffre.
	 * 
	 * @param chaine
	 *            La chaîne à contrôler.
	 * @return {@code true} si la chaîne contient au moins un chiffre,
	 *         {@code false} sinon.
	 */
	public static boolean containSpecialChar(final String chaine) {
		return RegexUtil.SPECIAL_CHAR_PATTERN.matcher(chaine).find();
	}

	/**
	 * Hashe le mot de passe passé en paramètre
	 * 
	 * @param password
	 *            Le mot de passe à hasher.
	 * @return Le mot de passe hashé.
	 */
	public static String hashPassword(final String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(15));
	}

	/**
	 * Compare un prétendu mot de passe avec le hash correspondant.
	 * 
	 * @param motDePasse
	 *            Le prétendu mot de passe, en clair.
	 * @param hashAComparer
	 *            Le mot de passe hashé à comparer.
	 * @return {@code true} si le mot de passe correspond au hash, {@code false}
	 *         sinon.
	 */
	public static boolean compareHash(final String motDePasse,
			final String hashAComparer) {
		return BCrypt.checkpw(motDePasse, hashAComparer);
	}
}
