package com.imie.util;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

import com.imie.entities.Fichier;
import com.imie.entities.Tag;
import com.imie.entities.Utilisateur;
import com.imie.services.impl.FichierServiceImpl;
import com.imie.services.impl.TagServiceImpl;
import com.imie.services.impl.UtilisateurServiceImpl;

public class TestInsertionVideo {
	@Test
	public void testInsert() {
		FichierServiceImpl fichierService = new FichierServiceImpl();
		UtilisateurServiceImpl utilisateurService = new UtilisateurServiceImpl();
		TagServiceImpl tagService = new TagServiceImpl();
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
