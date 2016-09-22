<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<div>
		<c:choose>
			<c:when test="${ resultats.isEmpty() }">
				Aucun m&eacute;dia ne correspond &agrave; votre recherche.
			</c:when>
			<c:otherwise>
				<c:forEach items="${ resultats }" var="media">
					<t:media media="${ media }" showPublieur="true"/>
				</c:forEach>
			</c:otherwise>
		</c:choose>			
		</div>
</t:genericpage>