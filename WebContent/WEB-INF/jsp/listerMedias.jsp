<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
<t:controleUtilisateurConnecte />
	<c:choose>
		<c:when test="${ sessionScope.utilisateur.medias.isEmpty() }">
			Vous n'avez pas de m&eacute;dias.<br/>
			<a href="./AjoutFichier">Y remédier !</a>
		</c:when>
		<c:otherwise>
			<fieldset>
				<legend>Liste de mes m&eacute;dias :</legend>
				<table>
					<c:forEach items="${ sessionScope.utilisateur.medias }" var="media">
						<tr>
							<td>
								<c:choose>
									<c:when test="${ media.isFichier() }">
										<%-- Définition de l'icone du média --%>
										<c:choose>
											<c:when test="${ media.isPhoto() }">
												<c:set scope="page" var="icone" value="icone_photo.png" />
											</c:when>
											<c:when test="${ media.isMusique() }">
												<c:set scope="page" var="icone" value="icone_musique.png" />
											</c:when>
											<c:when test="${ media.isVideo() }">
												<c:set scope="page" var="icone" value="icone_video.png" />
											</c:when>
										</c:choose>
										
										<a href="<c:out value="ConsultationMedia?media=${ media.id }" />" class="ligneMediaLien">
											<img src="./inc/images/<c:out value="${ icone }" />" class="mediaIcon" />
											<c:out value="${ media.titre }" />
										</a>
									</c:when>
									<c:otherwise>
										<c:out value="${ media.titre }" />
									</c:otherwise>
								</c:choose>
							</td>
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
		</c:otherwise>
	</c:choose>
</t:genericpage>