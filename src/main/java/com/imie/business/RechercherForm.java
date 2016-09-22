package com.imie.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.imie.entities.Media;
import com.imie.services.impl.MediaService;

public class RechercherForm extends AbstractBusiness {
	
	private static final MediaService mediaService = new MediaService();
	
	public List<Media> rechercher(final HttpServletRequest request) {
		final List<Media> resultats = new ArrayList<Media>();
		
		final String criteresRechercheString = getValeurChamp(request, "criteres");
		final List<String> listeCriteres = Arrays.asList(criteresRechercheString.split(" "));
		
		return mediaService.search(listeCriteres);
	}
}
