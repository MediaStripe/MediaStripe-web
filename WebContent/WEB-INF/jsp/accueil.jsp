<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<div>
			<c:forEach items="${ listeMedia }" var="media">
				<c:if test="${ media.publique }">
					<p class="ligneMedia">
						<a href="./ConsultationMedia?media=<c:out value="${ media.id }"/>" class="ligneMediaLien">
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
							
							<img src="./inc/images/<c:out value="${ icone }" />" class="mediaIcon" /><c:out value="${media.titre }"/><br/>
						</a>
						<a href="./ConsultationProfil?utilisateur=<c:out value="${ media.publieur.id }"/>" class="publieur">
							<c:out value="${ media.publieur.nom } ${ media.publieur.prenom }" />
						</a>
					</p>
				</c:if>
			</c:forEach>
		</div>
</t:genericpage>