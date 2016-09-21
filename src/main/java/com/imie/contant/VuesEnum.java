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
	/** Gestion du compte utilisateur. */
	GESTION_COMPTE("gestionCompte"),
	/** Modification des données de l'utilisateur. */
	MODIF_UTILISATEUR("modificationUtilisateur"),
	/** Modification du mot de passe d'un utilisateur. */
	MODIF_MOTDEPASSE("modificationMotdepasse"),
	/** Upload d'un média. */
	AJOUT_FICHIER("ajoutFichier"),
	/** Consultation des médias d'un utilisateur. */
	LISTER_MEDIAS("listerMedias"),
	/** Consultation d'un média. */
	CONSULTATION_MEDIA("consultationMedia"),
	/** Redirection vers la page de connexion. */
	REDIRECT_CONNEXION("redirectConnexion"),
	/** Modification d'un média. */
	MODIFICATION_MEDIA("modificationMedia"),
	/** Suppression d'un média. */
	SUPPRESSION_MEDIA("suppressionMedia"),
	/** Consultation du profil d'un utilisateur. */
	CONSULTATION_PROFIL("consultationProfil"),
	/** Consultation des contacts de l'utilisateur. */
	LISTER_CONTACTS("listerContacts"),
	;

	/** Le nom de la page JSP correspondant à la vue. */
	private String value;

	/**
	 * Initialise l'énumération à partir de la donnée en paramètre en y
	 * préfixant le chemin vers les pages JSP et en sufixant avec l'extension
	 * {@code .jsp}.
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
