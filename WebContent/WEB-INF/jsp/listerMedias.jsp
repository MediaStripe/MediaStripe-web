<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
<t:controleUtilisateurConnecte />
	<c:choose>
		<c:when test="${ sessionScope.utilisateur.medias.isEmpty() }">
			Vous n'avez pas de m&eacute;dias.<br/>
			<a href="./AjoutFichier">Y remédier !</a>
		</c:when>
		<c:otherwise>
			<c:forEach items="${ sessionScope.utilisateur.medias }" var="media">
						<c:choose>
							<c:when test="${ media.isFichier() }">
								<t:media media="${ media }" showPublieur="false" espacePerso="true" /> 
							</c:when>
							<c:otherwise>
								<c:out value="${ media.titre }" />
							</c:otherwise>
						</c:choose>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</t:genericpage>