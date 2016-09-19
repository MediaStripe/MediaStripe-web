package com.imie.contant;

import java.util.HashMap;
import java.util.Map;

public class ApplicationProperties {
	private static Map<String, String> properties;
	
	public static final String EXTENSION_SEPARATOR = ",";

	/**
	 * Retourne la propriété correspondant au nom passé en paramètre.<br/>
	 * Si la propriété n'existe pas, la fonction retourne {@code null}.
	 * 
	 * @param property
	 *            Le nom de la propriété voulue.
	 * @return La valeur de la propriété, ou {@code null} si elle n'existe pas.
	 */
	public static String get(final String property) {
		if (properties == null) {
			properties = new HashMap<String, String>();
			load();
		}
		return properties.get(property);
	}

	/**
	 * Charge les propriétés de l'application depuis les fichiers de
	 * configuration.
	 */
	// TODO : charger les propriétés depuis un fichier de configuration
	private static void load() {
		properties.put("media.files.repository", "/opt/MediaStripe/files/");
		
		properties.put("media.extensions.videos", "avi,wmv,flv,mp4");
		properties.put("media.extensions.musiques", "mp3,wav,ogg");
		properties.put("media.extensions.photos", "png,jpg,jpeg,gif");
	}
}
