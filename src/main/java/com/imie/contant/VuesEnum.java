package com.imie.contant;

/**
 * Énumération contenant les différentes vues, avec le chemin de leur page JSP.
 * @author takiguchi
 *
 */
public enum VuesEnum {
	/** Accueil du site. */
	ACCUEIL("accueil"),
	/** Connexion utilisateur. */
	CONNEXION("connexionForm"),
	/** Déconnexion utilisateur. */
	DECONNEXION("deconnexion"),
	/** Inscription, création d'utilisateurs. */
	INSCRIPTION("inscriptionForm"),
	/** Modification des données de l'utilisateur. */
	MODIF_UTILISATEUR("modificationUtilisateur"),
	/** Modification du mot de passe d'un utilisateur. */
	MODIF_MOTDEPASSE("modificationMotDePasse"),

	;

	/** Le nom de la page JSP correspondant à la vue. */
	private String value;

	/**
	 * Initialise l'énumération à partir de la donnée en paramètre en y
	 * préfixant le chemin vers les pages JSP et en sufixant avec l'extension
	 * {@code .JSP}.
	 * 
	 * @param value
	 */
	private VuesEnum(final String value) {
		this.value = "/WEB-INF/jsp/" + value + ".jsp";
	}

	/**
	 * Retourne le nom de la page JSP correspondant à la vue
	 * 
	 * @return Le nom de la page JSP correspondant à la vue
	 */
	public String val() {
		return value;
	}
}
