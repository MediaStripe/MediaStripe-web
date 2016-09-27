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
		<p id="header">
			<a href="./Accueil">
				<img src="./inc/images/mediastripe-logo.png" id="logo" />
			</a>
		</p>
		<div id="content">
			<t:menuUtilisateur/>
			
			<div id="subcontent<c:if test="${ !empty sessionScope.utilisateur }">Connected</c:if>">
				<t:formulaireDeRecherches criteres="${ criteres }"/>
				<jsp:doBody/>
			</div>
			
			<c:if test="${ !empty sessionScope.utilisateur }">
				<t:chat />
			</c:if>
		</div>
		<script src="http://code.jquery.com/jquery-3.1.0.min.js"></script>
		<script type="text/javascript" src="./inc/js/monjs.js"></script>
	</body>
</html>