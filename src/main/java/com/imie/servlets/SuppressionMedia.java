package com.imie.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.constant.VuesEnum;
import com.imie.entities.Media;
import com.imie.entities.Tag;
import com.imie.entities.Utilisateur;
import com.imie.services.MediaService;
import com.imie.util.SessionUtils;

/**
 * Servlet implementation class SuppressionMedia
 */
@WebServlet("/SuppressionMedia")
public class SuppressionMedia extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Inject
	private MediaService mediaService;
	
	private static final String VUE = VuesEnum.SUPPRESSION_MEDIA.val();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuppressionMedia() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionUtils.checkUtilisateurConnecte(this, request, response);
		
		final Media media = mediaService.findById(Integer.parseInt(request.getParameter("media")));
		
		final String resultat;
		
		final Utilisateur utilisateurConnecte = SessionUtils.getUtilisateurConnecte(request);
		
		if(media.getPublieur().equals(utilisateurConnecte)) {
			utilisateurConnecte.getMedias().remove(media);
			for(final Tag tag : media.getListeTags()) {
				tag.getMediasAssocies().remove(media);
			}
			mediaService.delete(media);
			resultat = "Média supprimé.";
		} else {
			resultat = "Vous n'avez pas les droits pour supprimer ce média.";
		}
		
		request.setAttribute("resultat", resultat);
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
