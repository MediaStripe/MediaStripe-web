<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
<t:controleUtilisateurConnecte />
		<fieldset>
			<legend>Mes contacts :</legend>
			<table>
				<c:forEach items="${ sessionScope.utilisateur.contacts }" var="contact">
					<tr>
						<td>
							<a href="./ConsultationProfil?utilisateur=<c:out value="${ contact.id }"/>" class="publieur">
								<c:out value="${ contact.nom } ${ contact.prenom }" />
							</a>
						</td>
						<td> 
							<a href="./SuppressionContact?utilisateur=<c:out value="${ contact.id }"/>" class="supprimer">
								<img src="./inc/images/supprimer.png" class="menuIcon" alt="Supprimer"/>
							</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</fieldset>
</t:genericpage>