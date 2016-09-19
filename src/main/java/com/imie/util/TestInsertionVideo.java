package com.imie.util;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

import com.imie.entities.Fichier;
import com.imie.entities.Tag;
import com.imie.entities.Utilisateur;
import com.imie.services.impl.FichierService;
import com.imie.services.impl.TagService;
import com.imie.services.impl.UtilisateurService;

public class TestInsertionVideo {
	@Test
	public void testInsert() {
		FichierService fichierService = new FichierService();
		UtilisateurService utilisateurService = new UtilisateurService();
		TagService tagService = new TagService();
		String result = "";
		
		try {
			// Récupération du user
			Utilisateur user = utilisateurService.findByEmail("florian.thierry72@gmail.com");
			
			// Création du media
			Fichier fichier = new Fichier();
			
			fichier.setTitre("Test");
			fichier.setDescription("Test");
			fichier.setDatecreation(new Timestamp(new Date().getTime()));
			fichier.setPublique(true);
			fichier.setPublieur(user);
			
			// Affectation des tags
			for(String libelle : new String[] { "Test", "Toto", "Titi", "Tata" }) {
				Tag tag = tagService.findByLibelle(libelle);
				
				if(tag == null) {
					tag = new Tag(libelle);
				}
				
				fichier.addTag(tag);
			}
			
			// Persistence du média
			fichierService.insert(fichier);
			
			result = "Insertion réussie !";
		} catch (Exception ex) {
			result = ex.getMessage() + ex.getStackTrace();
			ex.printStackTrace();
		}
		
		System.out.println("Résultat du traitement : " + result);
	}
}
