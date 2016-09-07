<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<fieldset>
			<legend>Ajout d'un média :</legend>
			<form action="./Upload" method="post" enctype="multipart/form-data">
				<label for="typeFichier">Type de fichier :</label>&nbsp;
				<select name="typeFichier" id="typeFichier" onchange="afficherForm()">
					<option value="video">Vid&eacute;o</option>
					<option value="photo">Photo</option>
					<option value="musique">Musique</option>
					<option value="film">Film</option>
					<option value="episode">&Eacute;pisode d'une s&eacute;rie</option>
				</select><br/>

				<label for="fichier">
					Emplacement du fichier<t:required/> :
				</label>&nbsp;<input type="file" name="fichier" id="fichier" required /><br/>
				<span class="erreur">${ form.listeErreurs['fichier'] }</span><br />

				<label for="titre">Titre<t:required/> :</label>&nbsp;
				<input type="text" name="titre" id="titre" value="<c:out value="${ fichier.titre }"/>" required /><br/>
				<span class="erreur">${ form.listeErreurs['titre'] }</span><br />

				<label for="description">Description<t:required/> :</label>&nbsp;
				<textarea name="description" id="description" placeholder="La description du fichier." required ><c:out value="${ description }"/></textarea><br/>
				<span class="erreur">${ form.listeErreurs['description'] }</span><br />

				<label for="publique">Publique<t:required/> :</label>&nbsp;
				<input type="checkbox" name="publique" id="publique" value="true" <c:if test="${ empty fichier || fichier.isPublique() }" >checked</c:if> /><br/>
				
				<label for="themePrincipal">Th&egrave;me principal :</label>
				<input type="text" name="themePrincipal" id="themePrincipal" value="<c:out value="${ fichier.mainTheme.libelle }" />" placeholder="Le th&egrave;me principal du fichier." /><br/>
				
				<label for="motsClefs">Mots clefs :</label>
				<input type="text" name="motsClefs" id="motsClefs" value="<c:out value="${ fichier.motsClefs }" />" placeholder="S&eacute;parez les mots clefs par des points-virgule" /><br/>
				
				<div id="filmForm">
					<label for="realisateur">R&eacute;alisateur<t:required/> :</label>
					<input type="text" name="realisateur" id="realisateur" value="<c:out value="${ realisateur }"/>" /><br/>
					<span class="erreur">${ form.listeErreurs['realisateur'] }</span><br />
				</div>
				<div id="episodeForm">
					<label for="numeroEpisode">Num&eacute;ro de l'&eacute;pisode<t:required/> :</label>
					<input type="text" name="numeroEpisode" id="numeroEpisode" value="<c:out value="${ numeroEpisode }"/>" /><br/>
					<span class="erreur">${ form.listeErreurs['numeroEpisode'] }</span><br />
					
					<label for="serieEpisode">S&eacute;rie de l'&eacute;pisode<t:required/> :</label>
					<input type="text" name="serieEpisode" id="serieEpisode" value="<c:out value="${ serieEpisode }"/>" /><br/>
					<span class="erreur">${ form.listeErreurs['serieEpisode'] }</span><br />
				</div>
				
				<input type="reset" value="R&eacute;initialiser" onclick="cacherForm()"/>
				<input type="submit" value="Valider"/><br/>
				
				<span class="erreur">${ form.listeErreurs['result'] }</span>
				<p class="info">${ form.getResultat() }</p>
			</form>
		</fieldset>
		<script type="text/javascript">
			function afficherForm() {
				var listeTypeFichier = document.getElementById("typeFichier");
				var valeurSelectionnee = listeTypeFichier.options[listeTypeFichier.selectedIndex].value;

				cacherForm();
				
				if("film" === valeurSelectionnee)
				{
					document.getElementById("filmForm").style.display = "inline";
				}
				else if("episode" === valeurSelectionnee)
				{
					document.getElementById("episodeForm").style.display = "inline";
				}
			}
			
			function cacherForm() {
				document.getElementById("filmForm").style.display = "none";
				document.getElementById("episodeForm").style.display = "none";
			}
		</script>
</t:genericpage>