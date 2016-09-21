<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<h2>
			<c:out value="${ utilisateurConsulte.nom }" /> 
			<c:out value="${ utilisateurConsulte.prenom }" />
			<c:if test="${ !empty sessionScope.utilisateur }">
				<c:choose>
					<c:when test="${ sessionScope.utilisateur.contacts.contains(utilisateurConsulte) }">
						<a href="./SuppressionContact?utilisateur=<c:out value="${ utilisateurConsulte.id }"/>" class="supprimer">
							<img src="./inc/images/supprimer.png" class="menuIcon" alt="Supprimer"/>
						</a>
					</c:when>
					<c:otherwise>
						<a href="./AjouterContact?utilisateur=<c:out value="${ utilisateurConsulte.id }"/>" class="ajouter">
							<img src="./inc/images/ajouter.png" class="menuIcon" alt="Supprimer"/>
						</a>
					</c:otherwise>
				</c:choose>
			</c:if>
		</h2>
		Adresse mail : <c:out value="${ utilisateurConsulte.mail }" /><br/>
		Inscrit depuis le <c:out value="${ utilisateurConsulte.dateinscription }" /><br/>
		<fieldset>
			<legend>Ses m&eacute;dias :</legend>
			<c:forEach items="${ utilisateurConsulte.medias }" var="media">
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
					</p>
				</c:if>
			</c:forEach>
		</fieldset>
</t:genericpage>