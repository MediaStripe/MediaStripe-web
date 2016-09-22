$(function() {
	/**
	 * Sélection d'un élément dans la liste déroulante nommée 'typeFichier' du
	 * formulaire "ajoutFichier".
	 */
	$("select[name='typeFichier']").change(function() {
		reinitialiserForm();
		
		var optionSelectionnee = $("select[name='typeFichier'] option:selected").val();
		
		if('film' === optionSelectionnee) {
			$.get('./inc/ajax/filmForm.jsp', undefined, afficherFilmForm);
		} else if('episode' === optionSelectionnee) {
			$.get('./inc/ajax/episodeForm.jsp', undefined, afficherEpisodeForm);
		}
	});
	
	/**
	 * Supprime les morceaux "ajout d'un film" et "ajout d'un épisode" du
	 * formulaire "ajoutFichier".
	 */
	function reinitialiserForm() {
		$('#filmForm').html('');
		$('#episodeForm').html('');
	}
	
	/**
	 * Affiche le morceau du formulaire d'ajout d'un film dans le formulaire
	 * "ajoutFichier".
	 * 
	 * @param data
	 *            Le contenu du formulaire d'ajout de film.
	 */
	function afficherFilmForm(data) {
		$('#filmForm').html(data);
	}
	
	/**
	 * Affiche le morceau du formulaire d'ajout d'un épisode de série dans le
	 * formulaire "ajoutFichier".
	 * 
	 * @param data
	 *            Le contenu du formulaire d'ajout d'épisode.
	 */
	function afficherEpisodeForm(data) {
		$('#episodeForm').html(data);
	}
	
	/**
	 * Clic sur une image "Suppression".
	 */
	$(".supprimer").click(function(event) {
		if(!confirm("Êtes-vous sûr de vouloir le supprimer ?")) {
			event.preventDefault();
		}
	});
	
	/**
	 * Clic sur la checkBox "toutcocher" dans le fomulaire de recherches.
	 */
	$('#toutcocher').click(function() {
		var checkBoxes = [
			'photo', 'musique', 'video', 'utilisateur'
		];
		
		cocherCheckBoxes(checkBoxes, $('#toutcocher').is(':checked'));
	});
	
	/**
	 * Coche les cases à cocher passées en paramètre en fonction de la variable
	 * "valeur".
	 * 
	 * @param valeur
	 *            Coche les checkBoxes si vaut "true", les décoche si vaut
	 *            "false".
	 */
	function cocherCheckBoxes(checkBoxes, valeur) {
		for (i = 0; i < checkBoxes.length; i++) {
			$('#' + checkBoxes[i]).prop('checked', valeur);
		}
		$('label[for = toutcocher]').text('Tout ' + (valeur ? 'dé' : '') + 'cocher');
	}
	
	$('.filtreCategorie').click(function() {
		var checkBoxes = [
		    $('#photo'),
		    $('#musique'),
		    $('#video'),
		    $('#utilisateur'),
		]
		var inputToutCocher = $('#toutcocher');
		
		if(getValeurCheckBox(checkBoxes[0]) 
				&& getValeurCheckBox(checkBoxes[1]) 
				&& getValeurCheckBox(checkBoxes[2]) 
				&& getValeurCheckBox(checkBoxes[3])) {
			
			
			inputToutCocher.prop('checked', true);
			$('label[for = toutcocher]').text('Tout décocher');
		} else {
			inputToutCocher.prop('checked', false);
			$('label[for = toutcocher]').text('Tout cocher');
		}
	});
	
	function getValeurCheckBox(checkBox) {
		return checkBox.is(':checked');
	}
});