<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${ empty sessionScope.utilisateur }">
	<c:redirect url="/Connexion">
		<c:param name="accessDenied"/>
	</c:redirect>
</c:if>