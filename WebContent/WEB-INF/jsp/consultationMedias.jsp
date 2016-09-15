<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<fieldset>
			<legend>Liste de mes m&eacute;dias :</legend>
			<ul>
				<c:forEach items="${ sessionScope.utilisateur.medias }" var="media">
					<c:choose>
						<c:when test="${ media.isFichier() }">
							<c:choose>
								<c:when test="${ media.isVideo() }">
									<c:set var="lienConsultationMedia" value="ConsultationVideo" scope="page" />
								</c:when>
								<c:when test="${ media.isPhoto() }">
									<c:set var="lienConsultationMedia" value="ConsultationPhoto" scope="page" />
								</c:when>
								<c:when test="${ media.isMusique() }">
									<c:set var="lienConsultationMedia" value="ConsultationMusique" scope="page" />
								</c:when>
							</c:choose>
							
							<li><a href="<c:out value="${ lienConsultationMedia }?media=${ media.id }" />" >${ media.titre }</a></li>
						</c:when>
						<c:otherwise>
							<li>${ media.titre }</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</fieldset>
</t:genericpage>