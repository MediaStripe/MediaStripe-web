package com.imie.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.contant.VuesEnum;

public final class SessionUtils {

	/**
	 * Indique si l'utilisateur est connecté et dispose d'une session en cours
	 * de validité.
	 * 
	 * @param request La requête de l'utilisateur. @return {@code true} si
	 * l'utilisateur est connecté, {@code false}. @throws IOException @throws
	 */
	public static void checkUtilisateurConnecte(final HttpServlet servlet, final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException, IOException {
		if ((request.getSession().getAttribute("utilisateur") == null)) {
			request.setAttribute("accessDenied", "Vous devez être connecté pour accéder à cette page.");
			servlet.getServletContext().getRequestDispatcher(VuesEnum.REDIRECT_CONNEXION.val()).forward(request, response);
		}
	}
}
