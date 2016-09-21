package com.imie.business;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.imie.contant.ApplicationProperties;
import com.imie.entities.Episode;
import com.imie.entities.Fichier;
import com.imie.entities.Film;
import com.imie.entities.Musique;
import com.imie.entities.Photo;
import com.imie.entities.Tag;
import com.imie.entities.Utilisateur;
import com.imie.entities.Video;
import com.imie.exceptions.BusinessException;
import com.imie.services.impl.FichierService;
import com.imie.services.impl.TagService;
import com.imie.services.impl.UtilisateurService;
import com.imie.util.RegexUtil;
import com.imie.util.StringUtil;

public class AjoutFichierForm extends AbstractBusiness {
	/** Taille du tampon pour le transfer du fichier depuis le client. */
	private static final int TAILLE_TAMPON = 10240;

	private static final Map<String, List<String>> listeExtensions;
	private static final String TAG_SEPARATOR = ";";

	static {
		listeExtensions = new HashMap<String, List<String>>();
		listeExtensions.put("video", new LinkedList<String>());
		listeExtensions.get("video").addAll(Arrays.asList(
				ApplicationProperties.get("media.extensions.videos").split(ApplicationProperties.EXTENSION_SEPARATOR)));

		listeExtensions.put("musique", new LinkedList<String>());
		listeExtensions.get("musique").addAll(Arrays.asList(ApplicationProperties.get("media.extensions.musiques")
				.split(ApplicationProperties.EXTENSION_SEPARATOR)));

		listeExtensions.put("photo", new LinkedList<String>());
		listeExtensions.get("photo").addAll(Arrays.asList(
				ApplicationProperties.get("media.extensions.photos").split(ApplicationProperties.EXTENSION_SEPARATOR)));

		listeExtensions.put("film", listeExtensions.get("video"));
		listeExtensions.put("episode", listeExtensions.get("video"));
	}

	// TODO : Corriger l'injection via @EJB
	@EJB
	private FichierService fichierService = new FichierService();

	// TODO : Corriger l'injecion via @EJB
	@EJB
	private UtilisateurService utilisateurService = new UtilisateurService();

	// TODO : Corriger l'injecion via @EJB
	@EJB
	private TagService tagService = new TagService();

	/**
	 * Procède aux contrôles métier avant d'effectuer l'enregistrement du
	 * nouveau media en base.
	 * 
	 * @param request
	 */
	public Fichier ajouterFichier(final HttpServletRequest request) {
		// Création d'un fichier vide;
		Fichier fichier = new Fichier();

		// Récupération du fichier envoyé
		Part part = null;
		try {
			part = request.getPart("fichier");
		} catch (IOException | ServletException ex) {
			ex.printStackTrace();
			setErreur("result", "Une erreur technique est survenue lors de l'envoi du fichier.");
		}

		if (part != null) {
			// Récupération des données du formulaire
			final String typeFichier = request.getParameter("typeFichier");
			final String titre = request.getParameter("titre");
			final String description = request.getParameter("description");
			final String themePrincipal = request.getParameter("themePrincipal");
			final String motsClefs = request.getParameter("motsClefs");
			final boolean publique = "on".equals(request.getParameter("publique"));

			// Construction du fichier en fonction de son type
			fichier = buildFichier(typeFichier);

			// Affectation des valeurs du formulaire au fichier
			fichier.setTitre(titre);
			fichier.setDescription(description);
			fichier.setPublique(publique);
			fichier.setMotsClefs(motsClefs);

			// Définition des données du fichier envoyé
			final String nomFichier = part.getSubmittedFileName();
			final String extension = nomFichier.substring(nomFichier.lastIndexOf(".") + 1);

			try {
				validerTypeFichier(typeFichier, extension);
			} catch (final BusinessException ex) {
				setErreur("fichier", ex.getMessage());
			}

			if (listeErreurs.isEmpty()) {
				final String nomFichierSortie = genererNomFichier(titre) + "." + extension;

				ecrireFichier(part, typeFichier, nomFichierSortie, ApplicationProperties.get("media.files.repository"));

				if (listeErreurs.isEmpty()) {
					final Utilisateur utilisateurConnecte = (Utilisateur) request.getSession()
							.getAttribute("utilisateur");
					fichier.setDatecreation(new Timestamp(new Date().getTime()));
					fichier.setCheminfichier(nomFichierSortie);

					if (fichier instanceof Film) {
						alimenterFilm(request, (Film) fichier);
					} else if (fichier instanceof Episode) {
						alimenterEpisode(request, (Episode) fichier);
					}

					fichier.setPublieur(utilisateurConnecte);

					// Construction du thème principal et des mots clefs du
					// fichier
					alimenterTags(fichier, themePrincipal, motsClefs);

					fichierService.insert(fichier);
				}
			}
			// En cas d'erreur, on supprime le fichier temporaire créé.
			else {
				// TODO : supprimer le fichier temporaire
			}

		}

		resultat = listeErreurs.isEmpty() ? "Ajout du fichier réussi."
				: "Une erreur est survenue lors de l'ajout du fichier.";

		return fichier;
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
	private void alimenterTags(final Fichier fichier, final String mainTheme, final String motsClefs) {
		if (StringUtil.isNull(mainTheme)) {
			fichier.setMainTheme(getTag(mainTheme));
		}

		for (String libelle : motsClefs.split(TAG_SEPARATOR)) {
			/*
			 * Contrôle de "isNull" au cas où il y aurait une saisie comme
			 * ";;;;;".
			 */
			if (StringUtil.isNull(mainTheme)) {
				fichier.addTag(getTag(libelle));
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
		final String libelleTimed = libelle.trim();
		System.out.println("Récupération du tag \"" + libelleTimed + "\"");
		Tag tag = tagService.findByLibelle(libelleTimed);

		if (tag == null) {
			System.out.println("Tag non trouvé");
			tag = new Tag(libelleTimed);
			tagService.insert(tag);
			System.out.println("Tag inséré : " + tag.toString());
		}

		System.out.println("Tag retourné : " + tag.toString());
		return tag;
	}

	/**
	 * Alimente les attributs spécifiques à la classe {@link Film} à partir des
	 * champs de la requette http.
	 * 
	 * @param request
	 * @param film
	 */
	private void alimenterFilm(final HttpServletRequest request, final Film film) {
		film.setRealisateur(request.getParameter("realisateur"));
	}

	/**
	 * Alimente les attributs spécifiques à la classe {@code Episode} à partir
	 * des champs de la requette http.
	 * 
	 * @param request
	 * @param episode
	 */
	private void alimenterEpisode(HttpServletRequest request, Episode episode) {
		boolean erreur = false;
		final String numeroEpisode = request.getParameter("numeroEpisode");

		try {
			validerNumeroEpisode(numeroEpisode);
		} catch (final BusinessException ex) {
			setErreur("numeroEpisode", ex.getMessage());
		}

		if (!erreur) {
			episode.setNumero(Integer.parseInt(numeroEpisode));
			episode.setSerie(request.getParameter("serieEpisode"));
		}
	}

	/**
	 * Effectue la validation du type du fichier.
	 * 
	 * @param typeFichier
	 *            Le type du fichier uploadé.
	 * @param extension
	 *            L'extension du fichier uploadé.
	 * @throws BusinessException
	 *             Si l'extension du fichier n'est pas prise en compte.
	 */
	private void validerTypeFichier(final String typeFichier, final String extension) throws BusinessException {
		if (!(listeExtensions.containsKey(typeFichier) && listeExtensions.get(typeFichier).contains(extension))) {
			throw new BusinessException("Le type du fichier n'est pas pris en compte.");
		}
	}

	/**
	 * Effectue la validation du numéro d'épisode.
	 * 
	 * @param numero
	 *            Le numéro d'épisode saisi.
	 * @throws BusinessException
	 *             Si le numéro d'épisode n'est pas une valeur numérique.
	 */
	private void validerNumeroEpisode(final String numero) throws BusinessException {
		if (!RegexUtil.isNumber(numero)) {
			throw new BusinessException("Le numéro de l'épisode doit être une valeur numérique");
		}
	}

	/**
	 * Retourne un objet correspondant au type fourni en paramètre.<br/>
	 * <br/>
	 * Exemple: buildFichier("video") retournera un fichier de class
	 * {@link com.imie.entities.Video}
	 * 
	 * @param typeFichier
	 * @return
	 */
	private Fichier buildFichier(final String typeFichier) {
		final Fichier fichier;

		switch (typeFichier) {
		case "video":
			fichier = new Video();
			break;
		case "film":
			fichier = new Film();
			break;
		case "episode":
			fichier = new Episode();
			break;
		case "photo":
			fichier = new Photo();
			break;
		case "musique":
			fichier = new Musique();
			break;
		default:
			throw new IllegalArgumentException(
					new StringBuilder("Type de fichier inconnu : ").append(typeFichier).toString());
		}

		return fichier;
	}

	/**
	 * Déplace le fichier temporaire de son répertoire vers le répertoire
	 * correspondant au type de fichier de ce dernier.<br/>
	 * <br/>
	 * Ex: Un fichier vidéo sera déplacé vers le fichier
	 * 
	 * @param part
	 *            Le fichier tempooraire récupéré à l'aide de
	 *            {@link HttpServletRequest#getPart(String) }.
	 * @param typeFichier
	 *            Le type de fichier. Ex: video, photo, musique etc...
	 * @param nomFichier
	 *            Le nom du fichier uploadé.
	 * @param chemin
	 *            Le chemin du répertoire contenant les fichiers-média, définit
	 *            via la propriété {@code "media.files.repository" }.
	 * @throws IOException
	 */
	private void ecrireFichier(final Part part, final String typeFichier, final String nomFichier,
			final String chemin) {
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;

		File fichier = null;

		try {
			System.out.println("Créaton du buffer d'entrée");
			entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);

			final String cheminFichierSortie = new StringBuilder(chemin).append(genererCheminTypeFichier(typeFichier))
					.append("/").append(nomFichier).toString();

			// TODO : Ajouter un contrôle sur le "/" à la fin du chemin
			fichier = new File(cheminFichierSortie);

			System.out.println("Création du fichier \"" + cheminFichierSortie + "\"");
			if (!fichier.createNewFile()) {
				throw new IOException(
						"Une erreur est survenue lors de la création du fichier \"" + cheminFichierSortie + "\"");
			}

			System.out.println("Créaton du buffer de sortie");
			sortie = new BufferedOutputStream(new FileOutputStream(fichier), TAILLE_TAMPON);

			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur;
			while ((longueur = entree.read(tampon)) > 0) {
				sortie.write(tampon, 0, longueur);
			}
		} catch (IOException e) {
			e.printStackTrace();
			setErreur("result",
					"Une erreur technique est survenue lors de l'enregistrement du fichier sur le serveur.");
			// En cas d'erreur, on supprime le fichier généré.
			if (fichier != null) {
				fichier.delete();
			}
		} finally {
			try {
				System.out.println("Fermeture du buffer de sortie");
				sortie.close();
			} catch (final Exception e) {
				e.printStackTrace();
			}
			try {
				System.out.println("Fermeture du buffer d'entrée");
				entree.close();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Génère le chemin depuis le répertoire de sauvegarde des fichiers de
	 * l'application vers le répertoire de destination du fichier.<br/>
	 * Par exemple, si "typeFichier" vaut "film", la fonction retournera
	 * "video/film".
	 * 
	 * @param typeFichier
	 *            Le type de fichier en cours d'enregistrement.
	 * @return Le chemin de destination du fichier en cours d'enregistrement.
	 */
	private String genererCheminTypeFichier(final String typeFichier) {
		final String result;

		switch (typeFichier) {
		case "film":
			result = "video/" + typeFichier;
			break;
		case "episode":
			result = "video/" + typeFichier;
			break;
		default:
			result = typeFichier;
		}

		return result;
	}

	/**
	 * Génère un nom de fichier unique en hashant le titre initial du fichier.
	 * 
	 * @param titreFichier
	 *            Le titre du fichier.
	 * @return Le nom du fichier généré.
	 */
	private String genererNomFichier(final String titreFichier) {
		final String hash = StringUtil.hashString(titreFichier, 4).substring(7);

		// Remplace les points par des tirets
		final String hashSansPoints = RegexUtil.replaceSequence(hash, "\\.", "-");
		// Remplace les slashs et les back-slashs par des a
		return RegexUtil.replaceSequence(hash, "[\\\\\\/]", "a");
	}
}
