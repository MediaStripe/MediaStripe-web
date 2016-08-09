package com.imie.business;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.imie.util.StringUtil;

public abstract class AbstractBusiness {
	/** Résultat du traitement effectué. */
	protected String resultat;
	
	/** Liste d'erreurs soulevées lors du traitement. */
	protected Map<String, String> listeErreurs = new HashMap<String, String>();
	
	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getListeErreurs() {
		return listeErreurs;
	}

	/**
	 * Enregistre une erreur dans la liste d'erreurs.
	 * @param champ Le nom du champ dont l'erreur a été générée.
	 * @param valeur Le message de l'erreur.
	 */
	public void setErreur(final String champ, final String valeur) {
		listeErreurs.put(champ, valeur);
	}
	
	/**
	 * Retourne la valeur du champ correspondant au nom passé en paramètre.
	 * @param request
	 * @param nomChamp Le nom du champ dans la requette.
	 * @return La valeur du champ ou {@code null} lorsque le champ n'est pas trouvé.
	 */
	protected static String getValeurChamp(final HttpServletRequest request, final String nomChamp) {
		final String valeurChamp = request.getParameter(nomChamp);
		return StringUtil.isNull(valeurChamp) ? null : valeurChamp;
	}
}
