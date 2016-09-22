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
				<input type="hidden" name="idMedia" value="<c:out value="${ media.id }"/>" />
				
				<label for="titre">Titre<t:required/> :</label>
				<input type="text" name="titre" id="titre" value="<c:out value="${ media.titre }" />" required />
				<span class="erreur">${ form.listeErreurs['titre'] }</span><br />
				
				<label for="description">Description<t:required/> :</label>
				<input type="text" name="description" id="description" value="<c:out value="${ media.description }" />"  required />
				<span class="erreur">${ form.listeErreurs['description'] }</span><br />
				
				<label for="publique">Publique :</label>
				<input type="checkbox" name="publique" id="publique" value="publique" <c:if test="${ media.publique }">checked</c:if> /><br/>
				
				<label for="themePrincipal">Th&egrave;me principal :</label>
				<input type="text" name="themePrincipal" id="themePrincipal" value="<c:out value="${ media.mainTheme.libelle }" />" placeholder="Le th&egrave;me principal du fichier." /><br/>
					
				<label for="motsClefs">Mots clefs :</label>
				<input type="text" name="motsClefs" id="motsClefs" value="<c:out value="${ media.motsClefs }" />" placeholder="S&eacute;parez les mots clefs par des points-virgule" /><br/>
				
				<c:choose>
					<c:when test="${ media.isFilm() }">
						<c:import url="includes/modificationFilmForm.jsp" />
					</c:when>
					<c:when test="${ media.isEpisode() }">
						<c:import url="includes/modificationEpisodeForm.jsp" />
					</c:when>
				</c:choose>

				<input type="reset" value="R&eacute;initialiser" />
				<input type="submit" value="Valider"/><br/>
				
				<span class="erreur">${ form.listeErreurs['result'] }</span>
				<p class="info">${ form.getResultat() }</p>
			</form>
		</fieldset>
</t:genericpage>