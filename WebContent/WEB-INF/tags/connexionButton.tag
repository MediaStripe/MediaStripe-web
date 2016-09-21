<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- TODO : Supprimer pour la prod.
Boutton permettant d'éviter le processus de connexion pour aller plus vite dans les tests. 
Ce bouton est appelé dans le fichier "genericpage.tag". --%>
<c:if test="${ empty sessionScope.utilisateur }">
	<form action="./Connexion" method="POST">
		<input type="hidden" name="mail" value="david.salmon@gmail.com" />
		<input type="hidden" name="password" value="P@ssword1"/>
		<input type="submit" value="ConnexionAuto"/>
	</form>
</c:if>