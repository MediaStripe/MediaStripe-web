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
					<t:media media="${ media }" showPublieur="false" />
				</c:if>
			</c:forEach>
		</fieldset>
</t:genericpage>