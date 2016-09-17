package com.imie.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.contant.VuesEnum;
import com.imie.entities.Utilisateur;

public final class SessionUtils {

	/**
	 * Indique si l'utilisateur est connecté et dispose d'une session en cours
	 * de validité.
	 * 
	 * @param request
	 *            La requête de l'utilisateur. @return {@code true} si
	 *            l'utilisateur est connecté, {@code false}. @throws
	 *            IOException @throws
	 */
	public static void checkUtilisateurConnecte(final HttpServlet servlet, final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException, IOException {
		if ((getUtilisateurConnecte(request) == null)) {
			request.setAttribute("accessDenied", "Vous devez être connecté pour accéder à cette page.");
			servlet.getServletContext().getRequestDispatcher(VuesEnum.REDIRECT_CONNEXION.val()).forward(request,
					response);
		}
	}

	/**
	 * Retourne l'utilisateur connecté, autrement dit, l'objet
	 * {@link Utilisateur} qui est mit en session.
	 * 
	 * @param request
	 *            La requête HTTP.
	 * @return L'utilisateur connecté, ou null si l'utilisateur n'est pas
	 *         connecté.
	 */
	public static Utilisateur getUtilisateurConnecte(final HttpServletRequest request) {
		return (Utilisateur) request.getSession().getAttribute("utilisateur");
	}
}
