package com.imie.business;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import com.imie.entities.Media;
import com.imie.entities.Tag;
import com.imie.exceptions.BusinessException;
import com.imie.services.impl.MediaService;
import com.imie.services.impl.TagService;
import com.imie.util.SessionUtils;

public class ModificationMediaForm extends AbstractBusiness {
	private static final String TAG_SEPARATOR = ";";
	
	// TODO : Corriger l'injection EJB
	@EJB
	private MediaService mediaService = new MediaService();
	
	@EJB
	private TagService tagService = new TagService();

	public void modifierMedia(final HttpServletRequest request) {
		final Media media = mediaService.findById(Integer.parseInt(request.getParameter("idMedia")));

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
			final boolean publique = "on".equals(request.getParameter("publique"));
			final String themePrincipal = request.getParameter("themePrincipal");
			final String motsClefs = request.getParameter("motsClefs");

			media.setTitre(titre);
			media.setDescription(description);
			media.setPublique(publique);
			
			alimenterTags(media, themePrincipal, motsClefs);
			supprimerTags(media, motsClefs);
			
			mediaService.update(media);
		}
	}
	
	private void supprimerTags(final Media fichier, String motsClefs) {
		for (String libelle : motsClefs.split(TAG_SEPARATOR)) {
			final Tag tag = tagService.findByLibelle(libelle);
			if(!fichier.getListeTags().contains(tag)) {
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
			if(!fichier.getListeTags().contains(tag)) {
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
