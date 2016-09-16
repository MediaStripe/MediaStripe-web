package com.imie.business;

import javax.servlet.http.HttpServletRequest;

import com.imie.entities.Fichier;
import com.imie.entities.Utilisateur;
import com.imie.exceptions.BusinessException;
import com.imie.services.impl.FichierService;

public class ConsultationMediaForm extends AbstractBusiness {
	// TODO : Corriger l'injection via @EJB
	// @EJB
	private FichierService fichierService = new FichierService();

	public Fichier getFichier(final HttpServletRequest request) {
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
				|| fichier.getPublieur().equals((Utilisateur) request.getSession().getAttribute("utilisateur")))) {
			throw new BusinessException(
					"Le fichier demandé est privé. Vous n'avez pas les droits suffisants pour y accéder.");
		}
	}
}
