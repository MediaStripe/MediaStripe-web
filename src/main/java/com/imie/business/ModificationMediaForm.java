package com.imie.business;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.imie.entities.Episode;
import com.imie.entities.Film;
import com.imie.entities.Media;
import com.imie.entities.Tag;
import com.imie.exceptions.BusinessException;
import com.imie.services.EpisodeService;
import com.imie.services.FilmService;
import com.imie.services.MediaService;
import com.imie.services.TagService;
import com.imie.util.SessionUtils;

public class ModificationMediaForm extends AbstractBusiness {
	private static final String TAG_SEPARATOR = ";";

	@Inject
	private MediaService mediaService;

	@Inject
	private EpisodeService episodeService;

	@Inject
	private FilmService filmService;

	@Inject
	private TagService tagService;

	public Media modifierMedia(final HttpServletRequest request) {
		/*
		 * La variable "media" peut être un film ou un épisode, qui ont un
		 * traitement spécial, et donc la variable va être ammenée à acceuillir
		 * sa représentation mais dans la bonne classe.
		 */
		Media media = mediaService.findById(Integer.parseInt(request.getParameter("idMedia")));

		try {
			validerDroitsModification(request, media);
		} catch (final BusinessException e) {
			setErreur("droits", e.getMessage());
		}

		resultat = listeErreurs.isEmpty() ? "Mise à jour effectuée."
				: "Échec de la mise à jour des informations personnelles.";

		if (!hasErrors()) {
			final String titre = request.getParameter("titre");
			final String description = request.getParameter("description");
			final boolean publique = "publique".equals(request.getParameter("publique"));
			final String themePrincipal = request.getParameter("themePrincipal");
			final String motsClefs = request.getParameter("motsClefs");

			/*
			 * Traitement spécifique à Episode et Film avant les modifs car les
			 * fonctions retournent l'objet mais dans une autre classe.
			 */
			if (media.isEpisode()) {
				media = modifierPartieEpisode(request, media);
			} else if (media.isFilm()) {
				media = modifierPartieFilm(request, media);
			}

			media.setTitre(titre);
			media.setDescription(description);
			media.setPublique(publique);

			alimenterTags(media, themePrincipal, motsClefs);
			supprimerTags(media, motsClefs);

			mediaService.update(media);
		}

		return media;
	}

	private Media modifierPartieEpisode(HttpServletRequest request, Media media) {
		final Episode episode = episodeService.findById(media.getId());
		episode.setNumero(Integer.parseInt(request.getParameter("numeroEpisode")));
		episode.setSerie(request.getParameter("serieEpisode"));
		return episode;
	}

	private Media modifierPartieFilm(HttpServletRequest request, Media media) {
		final Film film = filmService.findById(media.getId());
		film.setRealisateur(request.getParameter("realisateur"));
		return film;
	}

	/**
	 * Supprime la relations entre le média à modifier et les tags
	 * n'apparaissant pas dans la liste de mots clefs du média.
	 * 
	 * @param fichier
	 *            Le média à modifier.
	 * @param motsClefs
	 *            La chaîne regroupant la liste des tags associés.
	 */
	private void supprimerTags(final Media fichier, String motsClefs) {
		for (String libelle : motsClefs.split(TAG_SEPARATOR)) {
			final Tag tag = tagService.findByLibelle(libelle);
			if (!fichier.getListeTags().contains(tag)) {
				fichier.getListeTags().remove(tag);
			}
		}
	}

	/**
	 * Gère les tags à associer au nouveau fichier.<br/>
	 * Si un des mots clefs saisis ne figure pas en base de données, il sera
	 * créé puis ajouté au fichier.
	 * 
	 * @param fichier
	 *            Le fichier en cours de création.
	 * @param mainTheme
	 *            Le thème principal du fichier.
	 * @param motsClefs
	 *            La liste des mots clefs du fichiers sous forme de chaîne
	 *            séparés par le sépatateur définit par la variable
	 *            {@code TAG_SEPARATOR}.
	 */
	private void alimenterTags(final Media fichier, final String mainTheme, final String motsClefs) {
		fichier.setMainTheme(getTag(mainTheme));

		for (String libelle : motsClefs.split(TAG_SEPARATOR)) {
			final Tag tag = getTag(libelle);
			if (!fichier.getListeTags().contains(tag)) {
				fichier.addTag(tag);

			}
		}
	}

	/**
	 * Retourne le tag correspondant au libellé passé en paramètre. S'il
	 * n'existe pas en base, il sera créé avant d'être retourné.
	 * 
	 * @param libelle
	 *            Le libellé du mot clef.
	 */
	private Tag getTag(final String libelle) {
		final String libelleTrimed = libelle.trim();
		System.out.println("Récupération du tag \"" + libelleTrimed + "\"");
		Tag tag = tagService.findByLibelle(libelleTrimed);

		if (tag == null) {
			System.out.println("Tag non trouvé");
			tag = new Tag(libelleTrimed);
			tagService.insert(tag);
			System.out.println("Tag inséré : " + tag.toString());
		}

		System.out.println("Tag retourné : " + tag.toString());
		return tag;
	}

	/**
	 * Vérifie que le publieur du média est bien l'utilisateur connecté, dans le
	 * cas contraire, une {@link BusinessException} est levée.
	 * 
	 * @param request
	 *            La requête HTTP.
	 * @param media
	 *            Le média à modifier.
	 * @throws BusinessException
	 *             Si l'utilisateur connecté n'est pas le publieur du média.
	 */
	private void validerDroitsModification(final HttpServletRequest request, final Media media)
			throws BusinessException {
		if (!media.getPublieur().equals(SessionUtils.getUtilisateurConnecte(request))) {
			throw new BusinessException("Droits insuffisant pour modifier ce média");
		}
	}

}
