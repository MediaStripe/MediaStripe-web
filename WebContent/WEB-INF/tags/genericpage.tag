<%@tag description="Template de base du site MediaStripe" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MediaStripe</title>
		<link type="text/css" rel="stylesheet" href="./inc/css/style.css" />
	</head>
	<body>
		<div id="header">
			MediaStripe
		</div>
		<div id="menu">
			<c:if test="${ !empty sessionScope.utilisateur }">
				<div>
					${ session.utilisateur.nom} ${ session.utilisateur.prenom }
				</div>
			</c:if>
			<div id="links">
				<a href="./Accueil">Accueil</a><br/>
				<c:choose>
					<c:when test="${ empty sessionScope.utilisateur }">
					
						<a href="./Inscription">S'inscrire</a><br/>
						<a href="./Connexion">Connexion</a>
					</c:when>
					<c:otherwise>
						<a href="./Deconnexion" >D&eacute;connexion</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<jsp:doBody/>
	</body>
</html>