package com.imie.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.imie.entities.Media;
import com.imie.entities.Musique;
import com.imie.entities.Photo;
import com.imie.entities.Utilisateur;
import com.imie.entities.Video;
import com.imie.services.impl.MediaService;

public class RechercherForm extends AbstractBusiness {

	private static final MediaService mediaService = new MediaService();

	public List<Media> rechercher(final HttpServletRequest request) {
		final List<Media> resultats = new ArrayList<Media>();

		final String criteresRechercheString = getValeurChamp(request, "criteres");
		final Map<String, Boolean> categories = getCategories(request);
		final List<String> listeCriteres = Arrays.asList(criteresRechercheString.split(" "));

		return mediaService.search(listeCriteres, categories);
	}

	private Map<String, Boolean> getCategories(final HttpServletRequest request) {
		final Map<String, Boolean> result = new HashMap<String, Boolean>();

		result.put(Photo.class.getSimpleName(), "photo".equals(request.getParameter("photo")));
		result.put(Musique.class.getSimpleName(), "musique".equals(request.getParameter("musique")));
		result.put(Video.class.getSimpleName(), "video".equals(request.getParameter("video")));		
//		result.put(Utilisateur.class.getSimpleName(), "utilisateur".equals(request.getParameter("utilisateur")));		

		/*
		 * Si toutes les catégories sont à "false", on va sélectionner toutes
		 * les catégories de média.
		 */
		int nombreValeursFalse = 0;
		for (final Map.Entry<String, Boolean> categorie : result.entrySet()) {
			if (!categorie.getValue()) {
				nombreValeursFalse++;
			}
		}
		if (nombreValeursFalse == result.size()) {
			result.replace(Photo.class.getName(), true);
			result.replace(Musique.class.getName(), true);
			result.replace(Video.class.getName(), true);
		}

		return result;
	}
}
