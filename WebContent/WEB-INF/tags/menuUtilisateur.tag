<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
			<div id="menu">
				<c:choose>
					<c:when test="${ empty sessionScope.utilisateur }">
						<a href="./Inscription">
							<img src="./inc/images/inscription.png" class="menuIcon"/>S'inscrire
						</a>
						<a href="./Connexion"><img src="./inc/images/connexion.png" class="menuIcon"/>Connexion</a>
					</c:when>
					<c:otherwise>
						<a href="./GestionCompte">
							<img src="./inc/images/gestion_compte.png" class="menuIcon" />${ sessionScope.utilisateur.nom } ${ sessionScope.utilisateur.prenom }
						</a>
						<a href="./Deconnexion" >
							<img src="./inc/images/deconnexion.png" class="menuIcon" />D&eacute;connexion
						</a>
						<a href="./AjoutFichier">Ajouter un m&eacute;dia</a>
						<a href="./ListerMedias">Consulter mes m&eacute;dias</a>
					</c:otherwise>
				</c:choose>
				<%-- TODO : Supprimer pour la prod. --%>
				<t:connexionButton/>
			</div>
					