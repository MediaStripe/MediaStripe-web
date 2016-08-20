package com.imie.contant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class ApplicationProperties {
	private static Map<String, String> properties;
	
	public static String get(final String property) {
		if(properties == null) {
//			properties = load();
			load();
		}
		return properties.get(property);
	}
	
//	public static String get(final String property) {
//		if(properties == null) {
//			properties = load();
//		}
//		return properties.getProperty(property);
//	}

//	private static Properties properties;
	
	private static void load() {
		final Properties properties = new Properties();
		
		final InputStream input = ApplicationProperties.class.getResourceAsStream("/WEB-INF/config/application.properties");

		try {
			properties.load(input);
			
			for (Object key : properties.keySet()) {
				final String clef = (String) key;
				ApplicationProperties.properties.put(clef, properties.getProperty(clef));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
