<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
<t:controleUtilisateurConnecte />
		<fieldset>
			<legend>Liste de mes m&eacute;dias :</legend>
			<table>
				<c:forEach items="${ sessionScope.utilisateur.medias }" var="media">
					<tr>
						<c:choose>
							<c:when test="${ media.isFichier() }">
								<td>
									<a href="<c:out value="ConsultationMedia?media=${ media.id }" />" >
										${ media.titre }
									</a>
								</td>
							</c:when>
							<c:otherwise>
								${ media.titre }
							</c:otherwise>
						</c:choose>
						<td>
							<a href="./ModificationMedia?media=<c:out value="${ media.id }"/>">
								<img src="./inc/images/editer.png" class="menuIcon" alt="Éditer"/>
							</a>
						</td>
						<td>
							<a href="./SuppressionMedia?media=<c:out value="${ media.id }"/>" class="supprimer">
								<img src="./inc/images/supprimer.png" class="menuIcon" alt="Supprimer"/>
							</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</fieldset>
</t:genericpage>