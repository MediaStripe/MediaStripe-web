package com.imie.business;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.imie.contant.ApplicationProperties;
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
		listeExtensions.get("video").addAll(Arrays.asList(new String[] { "avi", "wmv", "flv", "mp4" }));
		listeExtensions.put("musique", new LinkedList<String>());
		listeExtensions.get("musique").addAll(Arrays.asList(new String[] { "mp3", "wav", "ogg" }));
		listeExtensions.put("photo", new LinkedList<String>());
		listeExtensions.get("photo").addAll(Arrays.asList(new String[] { "png", "jpg", "jpeg", "gif" }));
	}

	// TODO : Corriger l'injection via @EJB
	@EJB
	private FichierService fichierService;

	public void ajouterFichier(final HttpServletRequest request) {

		Part part = null;
		try {
			part = request.getPart("fichier");
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (final ServletException ex) {
			ex.printStackTrace();
		}

		if (part != null) {
			final String typeFichier = request.getParameter("typeFichier");
			final String titre = request.getParameter("titre");
			final String description = request.getParameter("description");
			final String nomFichier = part.getSubmittedFileName();
			final String extension = nomFichier.substring(nomFichier.lastIndexOf(".") + 1);

			try {
				validerTypeFichier(typeFichier, extension);
			} catch(final BusinessException ex) {
				setErreur("typeFichier", ex.getMessage());
			}
			
			try {
				ecrireFichier(part, genererNomFichier(titre).concat(".").concat(extension),
						ApplicationProperties.get("media.files.repository"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
	
	private void validerTypeFichier(final String typeFichier, final String extension) throws BusinessException {
		if(!(listeExtensions.containsKey(typeFichier) && listeExtensions.get(typeFichier).contains(extension))) {
			throw new BusinessException("Le type du fichier n'est pas pris en compte.");
		}
	}
	
	/**
	 * 
	 * @param nomAttribut
	 * @param part
	 * @return
	 */
	private String getAttribut(final String nomAttribut, final Part part) {
		String result = null;

		for (final String contentDispositon : part.getHeader(
				"content-disposition").split(";")) {
			if (contentDispositon.trim().startsWith(nomAttribut)) {
				result = contentDispositon.substring(contentDispositon
						.indexOf('=') + 1);
			}
		}
		

		return result;
	}

	private void ecrireFichier(final Part part, final String nomFichier,
			final String chemin) throws IOException {
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;

		try {
			entree = new BufferedInputStream(part.getInputStream(),
					TAILLE_TAMPON);
			sortie = new BufferedOutputStream(new FileOutputStream(new File(
					new StringBuilder(chemin).append(nomFichier).toString())),
					TAILLE_TAMPON);

			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur;
			while ((longueur = entree.read(tampon)) > 0) {
				sortie.write(tampon, 0, longueur);
			}
		} finally {
			try {
				sortie.close();
			} catch (final IOException e) {

			}
			try {
				entree.close();
			} catch (final IOException e) {

			}
		}
	}

	/**
	 * Génère un nom de fichier unique en hashant le titre du fichier.
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
		return RegexUtil.replaceSequence(hash, "\\\\\\/", "a");
	}
}
