<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
<t:controleUtilisateurConnecte />
		<fieldset>
			<legend>Liste de mes m&eacute;dias :</legend>
			<ul>
				<c:forEach items="${ sessionScope.utilisateur.medias }" var="media">
					<li>
						<c:choose>
							<c:when test="${ media.isFichier() }">
								<a href="<c:out value="ConsultationMedia?media=${ media.id }" />" >${ media.titre }</a>
							</c:when>
							<c:otherwise>
								${ media.titre }
							</c:otherwise>
						</c:choose>
						<a href="./ModificationMedia?media=<c:out value="${ media.id }"/>">Modifier</a>
					</li>
				</c:forEach>
			</ul>
		</fieldset>
</t:genericpage>