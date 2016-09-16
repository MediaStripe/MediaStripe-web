<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
<t:controleUtilisateurConnecte />
		<fieldset>
			<legend>Liste de mes m&eacute;dias :</legend>
			<ul>
				<c:forEach items="${ sessionScope.utilisateur.medias }" var="media">
					<c:choose>
						<c:when test="${ media.isFichier() }">
							<li><a href="<c:out value="ConsultationMedia?media=${ media.id }" />" >${ media.titre }</a></li>
						</c:when>
						<c:otherwise>
							<li>${ media.titre }</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</fieldset>
</t:genericpage>