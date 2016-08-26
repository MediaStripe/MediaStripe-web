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
import com.imie.entities.Video;
import com.imie.exceptions.BusinessException;
import com.imie.services.impl.FichierService;
import com.imie.util.RegexUtil;
import com.imie.util.StringUtil;

public class UploadForm extends AbstractBusiness {
	/** Taille du tampon pour le transfer du fichier depuis le client. */
	private static final int TAILLE_TAMPON = 10240;

	private static final Map<String, List<String>> listeExtensions;

	static {
		listeExtensions = new HashMap<String, List<String>>();
		listeExtensions.put("video", new LinkedList<String>());
		listeExtensions.get("video").addAll(
				Arrays.asList(ApplicationProperties.get(
						"media.extensions.videos").split(
						ApplicationProperties.EXTENSION_SEPARATOR)));

		listeExtensions.put("musique", new LinkedList<String>());
		listeExtensions.get("musique").addAll(
				Arrays.asList(ApplicationProperties.get(
						"media.extensions.musiques").split(
						ApplicationProperties.EXTENSION_SEPARATOR)));

		listeExtensions.put("photo", new LinkedList<String>());
		listeExtensions.get("photo").addAll(
				Arrays.asList(ApplicationProperties.get(
						"media.extensions.photos").split(
						ApplicationProperties.EXTENSION_SEPARATOR)));

	}

	// TODO : Corriger l'injection via @EJB
	@EJB
	private FichierService fichierService = new FichierService();

	/**
	 * Procède aux contrôles métier avant d'effectuer l'enregistrement du
	 * nouveau media en base.
	 * 
	 * @param request
	 */
	public void ajouterFichier(final HttpServletRequest request) {

		Part part = null;
		try {
			part = request.getPart("fichier");
		} catch (IOException | ServletException ex) {
			ex.printStackTrace();
			setErreur("result",
					"Une erreur technique est survenue lors de l'envoi du fichier.");
		}

		if (part != null) {
			final String typeFichier = request.getParameter("typeFichier");
			final String titre = request.getParameter("titre");
			final String description = request.getParameter("description");
			final String nomFichier = part.getSubmittedFileName();
			final String extension = nomFichier.substring(nomFichier
					.lastIndexOf(".") + 1);

			try {
				validerTypeFichier(typeFichier, extension);
			} catch (final BusinessException ex) {
				setErreur("fichier", ex.getMessage());
			}

			if (listeErreurs.isEmpty()) {
				final String nomFichierSortie = genererNomFichier(titre)
						.concat(".").concat(extension);

				ecrireFichier(part, typeFichier, nomFichierSortie,
						ApplicationProperties.get("media.files.repository"));

				if (listeErreurs.isEmpty()) {
					final Fichier fichier = buildFichier(typeFichier);

					fichier.setTitre(titre);
					fichier.setDescription(description);
					fichier.setDatecreation(new Timestamp(new Date().getTime()));
					fichier.setCheminfichier(nomFichierSortie);

					if (fichier instanceof Film) {
						alimenterFilm(request, (Film) fichier);
					} else if (fichier instanceof Episode) {
						alimenterEpisode(request, (Episode) fichier);
					}

					fichierService.insert(fichier);
				}
			}
			// En cas d'erreur, on supprime le fichier temporaire créé.
			else {
				// TODO : supprimer le fichier temporaire
			}

			resultat = listeErreurs.isEmpty() ? "Ajout du fichier réussi."
					: "Une erreur est survenue lors de l'ajout du fichier.";
		}
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

		if (erreur) {
			episode.setNumero(Integer.parseInt(numeroEpisode));
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
	private void validerTypeFichier(final String typeFichier,
			final String extension) throws BusinessException {
		if (!(listeExtensions.containsKey(typeFichier) && listeExtensions.get(
				typeFichier).contains(extension))) {
			throw new BusinessException(
					"Le type du fichier n'est pas pris en compte.");
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
	private void validerNumeroEpisode(final String numero)
			throws BusinessException {
		if (!RegexUtil.isNumber(numero)) {
			throw new BusinessException(
					"Le numéro de l'épisode doit être une valeur numérique");
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
			throw new IllegalArgumentException(new StringBuilder(
					"Type de fichier inconnu : ").append(typeFichier)
					.toString());
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
	private void ecrireFichier(final Part part, final String typeFichier,
			final String nomFichier, final String chemin) {
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;

		File fichier = null;

		try {
			System.out.println("Créaton du buffer d'entrée");
			entree = new BufferedInputStream(part.getInputStream(),
					TAILLE_TAMPON);

			final String cheminFichierSortie = new StringBuilder(chemin)
					.append(typeFichier).append("/").append(nomFichier)
					.toString();

			// TODO : Ajouter un contrôle sur le "/" à la fin du chemin
			fichier = new File(cheminFichierSortie);

			System.out.println("Création du fichier \"" + cheminFichierSortie
					+ "\"");
			if (!fichier.createNewFile()) {
				throw new IOException(
						"Une erreur est survenue lors de la création du fichier \""
								+ cheminFichierSortie + "\"");
			}

			System.out.println("Créaton du buffer de sortie");
			sortie = new BufferedOutputStream(new FileOutputStream(fichier),
					TAILLE_TAMPON);

			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur;
			while ((longueur = entree.read(tampon)) > 0) {
				sortie.write(tampon, 0, longueur);
			}
		} catch (IOException e) {
			e.printStackTrace();
			setErreur(
					"result",
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
	 * Génère un nom de fichier unique en hashant le titre initial du fichier.
	 * 
	 * @param titreFichier
	 *            Le titre du fichier.
	 * @return Le nom du fichier généré.
	 */
	private String genererNomFichier(final String titreFichier) {
		final String hash = StringUtil.hashString(titreFichier, 4).substring(7);

		// Remplace les points par des tirets
		final String hashSansPoints = RegexUtil.replaceSequence(hash, "\\.",
				"-");
		// Remplace les slashs et les back-slashs par des a
		return RegexUtil.replaceSequence(hash, "[\\\\\\/]", "a");
	}
}