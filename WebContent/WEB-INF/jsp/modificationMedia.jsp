<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:genericpage>
<t:controleUtilisateurConnecte />
		<fieldset>
			<legend>
				Modification du média
				<c:out value="${ media.titre }" />
				(<c:out value="${ media.id }" />) :
			</legend>
			<form action="./ModificationMedia" method="post" accept-charset="UTF-8">
				<table>
					<tr>
						<td>
							<input type="hidden" name="idMedia" value="<c:out value="${ media.id }"/>" />
				
							<label for="titre">Titre<t:required/> :</label>
						</td>
						<td>
							<input type="text" name="titre" id="titre" value="<c:out value="${ media.titre }" />" required />
							<span class="erreur">${ form.listeErreurs['titre'] }</span><br />
						</td>
					</tr>
					<tr>
						<td>
							<label for="description">Description<t:required/> :</label>
						</td>
						<td>
							<input type="text" name="description" id="description" value="<c:out value="${ media.description }" />"  required />
							<span class="erreur">${ form.listeErreurs['description'] }</span><br />
						</td>
					</tr>
					<tr>
						<td>
							<label for="publique">Publique :</label>
						</td>
						<td>
							<input type="checkbox" name="publique" id="publique" value="publique" <c:if test="${ media.publique }">checked</c:if> />
						</td>
					</tr>
					<tr>
						<td>
							<label for="themePrincipal">Th&egrave;me principal :</label>
						</td>
						<td>
							<input type="text" name="themePrincipal" id="themePrincipal" value="<c:out value="${ media.mainTheme.libelle }" />" placeholder="Le th&egrave;me principal du fichier." />
						</td>
					</tr>
					<tr>
						<td>
							<label for="motsClefs">Mots clefs :</label>
						</td>
						<td>
							<input type="text" name="motsClefs" id="motsClefs" value="<c:out value="${ media.motsClefs }" />" placeholder="S&eacute;parez les mots clefs par des points-virgule" />
						</td>
					</tr>
							<c:choose>
								<c:when test="${ media.isFilm() }">
									<c:import url="includes/modificationFilmForm.jsp" />
								</c:when>
								<c:when test="${ media.isEpisode() }">
									<c:import url="includes/modificationEpisodeForm.jsp" />
								</c:when>
							</c:choose>
				</table>

				<input type="reset" value="R&eacute;initialiser" />
				<input type="submit" value="Valider"/><br/>
				
				<span class="erreur">${ form.listeErreurs['result'] }</span>
				<p class="info">${ form.getResultat() }</p>
			</form>
		</fieldset>
</t:genericpage>