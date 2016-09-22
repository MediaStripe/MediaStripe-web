<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<div>
			<c:forEach items="${ listeMedia }" var="media">
				<c:if test="${ media.publique }">
					<t:media media="${ media }" showPublieur="true" />
				</c:if>
			</c:forEach>
		</div>
</t:genericpage>