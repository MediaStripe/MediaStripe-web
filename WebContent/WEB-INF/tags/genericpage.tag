<%@tag description="Template de base du site MediaStripe"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MediaStripe</title>
		<link type="text/css" rel="stylesheet" href="./inc/css/style.css" />
	</head>
	<body>
		<div id="header">MediaStripe</div>
		<div id="menu">
			<%-- TODO : Supprimer pour la prod. --%>
			<t:connexionButton/>
			<div id="links">
				<a href="./Accueil">Accueil</a><br/>
				<c:choose>
					<c:when test="${ empty sessionScope.utilisateur }">
						<a href="./Inscription">S'inscrire</a><br/>
						<a href="./Connexion"><img src="./inc/images/connexion.png" class="menuIcon" alt="C"/> Connexion</a>
					</c:when>
					<c:otherwise>
						<t:menuUtilisateur />
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div id="content">
			<jsp:doBody/>
		</div>
		<c:if test="${ !empty sessionScope.utilisateur }">
			<t:chat />
		</c:if>
		<script src="http://code.jquery.com/jquery-3.1.0.min.js"></script>
		<script type="text/javascript" src="./inc/js/monjs.js"></script>
	</body>
</html>