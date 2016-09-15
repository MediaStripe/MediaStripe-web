package com.imie.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccessFichier
 */
@WebServlet(urlPatterns = "/fichier/*",
	initParams = {
		@WebInitParam(name= "repertoire", value="/opt/MediaStripe/files/")
	}
)
public class AccessFichier extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int TAILLE_TAMPON = 10240; // 10ko
       
	private String repertoire;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccessFichier() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("##################################################################");
		System.out.println("AccessFichier !!!");
		System.out.println("##################################################################");
		
		if(repertoire == null) {
			repertoire = getServletConfig().getInitParameter("repertoire");
		}
		
		/*
         * Récupération du chemin du fichier demandé au sein de l'URL de la
         * requête
         */
        String fichierRequis = request.getPathInfo();
        System.out.println("Chemin de la requete : " + fichierRequis);

        /* Vérifie qu'un fichier a bien été fourni */
        if ( fichierRequis == null ) {
            /*
             * Si non, alors on envoie une erreur 404, qui signifie que la
             * ressource demandée n'existe pas
             */
            response.sendError( HttpServletResponse.SC_NOT_FOUND );
            return;
        }
        
        /*
         * Décode le nom de fichier récupéré, susceptible de contenir des
         * espaces et autres caractères spéciaux, et prépare l'objet File
         */
        fichierRequis = URLDecoder.decode( fichierRequis, "UTF-8" );
        File fichier = new File( repertoire, fichierRequis );
        
        System.out.println("Fichier demandé : " + fichier.getAbsolutePath());
        
        /* Vérifie que le fichier existe bien */
        if ( !fichier.exists() ) {
            /*
             * Si non, alors on envoie une erreur 404, qui signifie que la
             * ressource demandée n'existe pas
             */
            response.sendError( HttpServletResponse.SC_NOT_FOUND );
            return;
        }

        /* Récupère le type du fichier */
        String type = getServletContext().getMimeType( fichier.getName() );

        /*
         * Si le type de fichier est inconnu, alors on initialise un type par
         * défaut
         */
        if ( type == null ) {
            type = "application/octet-stream";
        }

        /* Initialise la réponse HTTP */
        response.reset();
        response.setBufferSize( TAILLE_TAMPON );
        response.setContentType( type );
        response.setHeader( "Content-Length", String.valueOf( fichier.length() ) );
        response.setHeader( "Content-Disposition", "attachment; filename=\"" + fichier.getName() + "\"" );

        /* Prépare les flux */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            /* Ouvre les flux */
            entree = new BufferedInputStream( new FileInputStream( fichier ), TAILLE_TAMPON );
            sortie = new BufferedOutputStream( response.getOutputStream(), TAILLE_TAMPON );

            /* Lit le fichier et écrit son contenu dans la réponse HTTP */
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur;
            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
                sortie.write( tampon, 0, longueur );
            }
        } finally {
            sortie.close();
            entree.close();
        }
	}

}
