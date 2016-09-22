<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
<t:controleUtilisateurConnecte />
		<fieldset>
			<legend>Ajout d'un média :</legend>
			<form action="./AjoutFichier" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
				<table>
					<tr>
						<td>
							<label for="typeFichier">Type de fichier :</label>
						</td>
						<td>
							<select name="typeFichier" id="typeFichier">
								<option value="video">Vid&eacute;o</option>
								<option value="photo">Photo</option>
								<option value="musique">Musique</option>
								<option value="film">Film</option>
								<option value="episode">&Eacute;pisode d'une s&eacute;rie</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<label for="fichier">
								Emplacement du fichier<t:required/> :
							</label>
						</td>
						<td>
							<input type="file" name="fichier" id="fichier" required /><br/>
							<span class="erreur">${ form.listeErreurs['fichier'] }</span>
						</td>
					</tr>
					<tr>
						<td>
							<label for="titre">Titre<t:required/> :</label>
						</td>
						<td>
							<input type="text" name="titre" id="titre" value="<c:out value="${ fichier.titre }"/>" required /><br/>
							<span class="erreur">${ form.listeErreurs['titre'] }</span>
						</td>
					</tr>
					<tr>
						<td>
							<label for="description">Description<t:required/> :</label>
						</td>
						<td>
							<input type="text" name="description" id="description" placeholder="La description du fichier." value="<c:out value="${ description }"/>" required >
							<span class="erreur">${ form.listeErreurs['description'] }</span>
						</td>
					</tr>
					<tr>
						<td>
							<label for="publique">Publique<t:required/> :</label>
						</td>
						<td>
							<input type="checkbox" name="publique" id="publique" value="publique" <c:if test="${ empty fichier || fichier.isPublique() }" >checked</c:if> />
						</td>
					</tr>
					<tr>
						<td>
							<label for="themePrincipal">Th&egrave;me principal :</label>
						</td>
						<td>
							<input type="text" name="themePrincipal" id="themePrincipal" value="<c:out value="${ fichier.mainTheme.libelle }" />" placeholder="Le th&egrave;me principal du fichier." />
						</td>
					</tr>
					<tr>
						<td>
							<label for="motsClefs">Mots clefs :</label>
						</td>
						<td>
							<input type="text" name="motsClefs" id="motsClefs" value="<c:out value="${ fichier.motsClefs }" />" placeholder="S&eacute;parez les mots clefs par des points-virgule" />
						</td>
					</tr>
				</table>
				
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