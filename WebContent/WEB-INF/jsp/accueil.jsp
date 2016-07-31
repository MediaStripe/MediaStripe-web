<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<h1>Bienvenue sur MediaStripe !</h1>
		<c:choose>
			<c:when test="${ empty sessionScope.utilisateur }">
				Vous n'etes pas connect&eacute;.
			</c:when>
			<c:otherwise>
				Vous etes connect&eacute; ! :D
			</c:otherwise>
		</c:choose>
</t:genericpage>