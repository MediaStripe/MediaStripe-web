package com.imie.contant;

/**
 * Champs du formulaire d'inscription.
 * @author takiguchi
 *
 */
public enum ChampsUtilisateurEnum {
	/** Nom de l'utilisateur. */
	NOM("nom"),
	/** Prénom de l'utilisateur. */
	PRENOM("prenom"),
	/** Adresse mail de l'utilisateur. */
	MAIL("mail"),
	/** Mot de passe de l'utilisateur. */
	PASSWORD("password");
	
	/** Valeur textuelle de l'énumération. */
	private String value;
	
	/** Constructeur par défaut. */
	private ChampsUtilisateurEnum(final String value) {
		this.value = value;
	}
	
	/**
	 * Retourne la valeur textuelle de l'énumération.
	 * @return La valeur textuelle de l'énumération.
	 */
	public String val() {
		return value;
	}
	
}
