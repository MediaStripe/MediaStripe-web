<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
<t:controleUtilisateurConnecte />
		<fieldset>
			<legend>Ajout d'un média :</legend>
			<form action="./AjoutFichier" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
				<label for="typeFichier">Type de fichier :</label>&nbsp;
				<select name="typeFichier" id="typeFichier">
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
				<input type="checkbox" name="publique" id="publique" value="publique" <c:if test="${ empty fichier || fichier.isPublique() }" >checked</c:if> /><br/>
				
				<label for="themePrincipal">Th&egrave;me principal :</label>
				<input type="text" name="themePrincipal" id="themePrincipal" value="<c:out value="${ fichier.mainTheme.libelle }" />" placeholder="Le th&egrave;me principal du fichier." /><br/>
				
				<label for="motsClefs">Mots clefs :</label>
				<input type="text" name="motsClefs" id="motsClefs" value="<c:out value="${ fichier.motsClefs }" />" placeholder="S&eacute;parez les mots clefs par des points-virgule" /><br/>
				
				<div id="filmForm">
				</div>
				<div id="episodeForm">
					
				</div>
				
				<input type="reset" value="R&eacute;initialiser" onclick="cacherForm()"/>
				<input type="submit" value="Valider"/><br/>
				
				<span class="erreur">${ form.listeErreurs['result'] }</span>
				<p class="info">${ form.getResultat() }</p>
			</form>
		</fieldset>
</t:genericpage>