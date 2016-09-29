package com.imie.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.business.ModificationMediaForm;
import com.imie.constant.VuesEnum;
import com.imie.entities.Media;
import com.imie.entities.Tag;
import com.imie.services.MediaService;
import com.imie.util.SessionUtils;

/**
 * Servlet implementation class ModifierMedia
 */
@WebServlet("/ModificationMedia")
public class ModificationMedia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VUE_FORM = VuesEnum.MODIFICATION_MEDIA.val();

	private static final String VUE_SUCCESS = VuesEnum.MODIFICATION_MEDIA.val();

	@Inject
	private ModificationMediaForm modificationMediaFrom;
	
	@Inject
	private MediaService mediaService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModificationMedia() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionUtils.checkUtilisateurConnecte(this, request, response);
		
		final Media media = mediaService.findById(Integer.parseInt(request.getParameter("media")));
		
		for(final Tag tag : media.getListeTags()) {
			media.setMotsClefs((media.getMotsClefs() == null ? "" : (media.getMotsClefs() + ";")) + tag.getLibelle());
		}
		
		request.setAttribute("media", media);

		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionUtils.checkUtilisateurConnecte(this, request, response);
		
		request.setCharacterEncoding("UTF-8");

		final Media media = modificationMediaFrom.modifierMedia(request);

		request.setAttribute("media", media);
		request.setAttribute("form", modificationMediaFrom);

		this.getServletContext().getRequestDispatcher(modificationMediaFrom.hasErrors() ? VUE_FORM : VUE_SUCCESS)
				.forward(request, response);
	}

}
