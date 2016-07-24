package com.imie.util;

/**
 * Méthodes génériques relatives au traitement de chaînes de caractères.
 * @author takiguchi
 *
 */
public final class StringUtil {
	
	/**
	 * Indique si la chaîne est nulle ou si elle est vide, c'est à dire composée que d'espaces.
	 * @param chaine La chaîne à tester.
	 * @return {@code true} si la chaîne est nulle ou vide, {@code false} sinon.
	 */
	public static boolean isNull(final String chaine) {
		return chaine == null || chaine.trim().length() == 0;
	}
	
	/**
	 * Indique si la chaîne passée en paramètre contient au moins une lettre minuscule.
	 * @param chaine La chaîne à contrôler.
	 * @return {@code true} si la chaîne contient au moins une lettre minuscule, {@code false} sinon.
	 */
	public static boolean containLowercase(final String chaine) {
		return RegexUtil.LETTRE_MIN_PATTERN.matcher(chaine).find();
	}
	
	/**
	 * Indique si la chaîne passée en paramètre contient au moins une lettre majuscule.
	 * @param chaine La chaîne à contrôler.
	 * @return {@code true} si la chaîne contient au moins une lettre majuscule, {@code false} sinon.
	 */
	public static boolean containUppercase(final String chaine) {
		return RegexUtil.LETTRE_MAJ_PATTERN.matcher(chaine).find();
	}
	
	/**
	 * Indique si la chaîne passée en paramètre contient au moins un chiffre.
	 * @param chaine La chaîne à contrôler.
	 * @return {@code true} si la chaîne contient au moins un chiffre, {@code false} sinon.
	 */
	public static boolean containNumber(final String chaine) {
		return RegexUtil.NUMBER_PATTERN.matcher(chaine).find();
	}
	
	/**
	 * Indique si la chaîne passée en paramètre contient au moins un chiffre.
	 * @param chaine La chaîne à contrôler.
	 * @return {@code true} si la chaîne contient au moins un chiffre, {@code false} sinon.
	 */
	public static boolean containSpecialChar(final String chaine) {
		return RegexUtil.SPECIAL_CHAR_PATTERN.matcher(chaine).find();
	}
}
