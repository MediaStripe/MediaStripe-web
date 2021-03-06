package com.imie.business;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.imie.entities.Fichier;
import com.imie.exceptions.BusinessException;
import com.imie.services.FichierService;
import com.imie.util.SessionUtils;

public class ConsultationMediaForm extends AbstractBusiness {
	@Inject
	private FichierService fichierService;

	public Fichier getFichier(final HttpServletRequest request) {
		reinit();
		
		final Fichier fichier = fichierService.findById(Integer.parseInt(request.getParameter("media")));
		
		try {
			validerDroitsFichier(request, fichier);
		} catch(final BusinessException e) {
			setErreur("droits", e.getMessage());
		}
		
		return fichier;
	}

	/**
	 * Vérifie les droits d'accès au fichier demandé.
	 * 
	 * @param request
	 *            La requête HTTP.
	 * @param fichier
	 *            Le fichier demandé par l'utilisateur.
	 * @throws BusinessException
	 *             Si l'utilisateur n'a pas les droits d'accès au fichier.
	 */
	private void validerDroitsFichier(HttpServletRequest request, Fichier fichier) throws BusinessException {
		if (!(fichier.isPublique()
				|| fichier.getPublieur().equals(SessionUtils.getUtilisateurConnecte(request)))) {
			throw new BusinessException(
					"Le fichier demandé est privé. Vous n'avez pas les droits suffisants pour y accéder.");
		}
	}
}
