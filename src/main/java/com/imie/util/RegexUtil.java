package com.imie.util;

import java.util.regex.Pattern;

public final class RegexUtil {
	
	private static final String EMAIL_REGEX = "^.*@.*\\..{2,}$";
	private static final String LETTRE_MIN_REGEX = ".*[a-z].*";
	private static final String LETTRE_MAJ_REGEX = ".*[a-z].*";
	private static final String NUMBER_REGEX = ".*[0-9].*";
	private static final String SPECIAL_CHAR_REGEX = ".*\\W.*";
	
	// La portée "package" permet à la classe StringUtil d'utiliser les patterns suivants :
	static final Pattern EMAIL_PATTERN;
	static final Pattern LETTRE_MIN_PATTERN;
	static final Pattern LETTRE_MAJ_PATTERN;
	static final Pattern NUMBER_PATTERN;
	static final Pattern SPECIAL_CHAR_PATTERN;
	
	static {
		EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
		LETTRE_MIN_PATTERN = Pattern.compile(LETTRE_MIN_REGEX);
		LETTRE_MAJ_PATTERN = Pattern.compile(LETTRE_MAJ_REGEX);
		NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
		SPECIAL_CHAR_PATTERN = Pattern.compile(SPECIAL_CHAR_REGEX);
	}
	
	/**
	 * Indique si la chaîne passée en paramètre est une adresse email.
	 * @param chaine La chaîne à contrôler.
	 * @return {@code true} si la chaîne est une adresse email, {@code false} sinon.
	 */
	public static boolean isEmail(final String chaine) {
		return EMAIL_PATTERN.matcher(chaine).find();
	}
	
	public static String replaceSequence(final String chaine, final String regex, final String sequenceRemplacante) {
		return Pattern.compile(regex).matcher(chaine).replaceAll(sequenceRemplacante);
	}
}
