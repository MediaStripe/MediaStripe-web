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
	 * Supprime les morceaux "ajout d'un film" et "ajout d'un épisode" du formulaire "ajoutFichier".
	 */
	function reinitialiserForm() {
		$('#filmForm').html('');
		$('#episodeForm').html('');
	}
	
	/**
	 * Affiche le morceau du formulaire d'ajout d'un film dans le formulaire "ajoutFichier".
	 * @param data Le contenu du formulaire d'ajout de film.
	 */
	function afficherFilmForm(data) {
		$('#filmForm').html(data);
	}
	
	/**
	 * Affiche le morceau du formulaire d'ajout d'un épisode de série dans le formulaire "ajoutFichier".
	 * @param data Le contenu du formulaire d'ajout d'épisode.
	 */
	function afficherEpisodeForm(data) {
		$('#episodeForm').html(data);
	}
});