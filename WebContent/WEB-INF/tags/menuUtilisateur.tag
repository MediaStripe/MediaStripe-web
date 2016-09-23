<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
				<div>
					<a href="./GestionCompte">
						${ sessionScope.utilisateur.nom } ${ sessionScope.utilisateur.prenom }
					</a>
					<a href="./Deconnexion" ><img src="./inc/images/deconnexion.png" class="menuIcon" alt="D"/>D&eacute;connexion</a>
					<a href="./AjoutFichier">Ajouter un m&eacute;dia</a>
					<a href="./ListerMedias">Consulter mes m&eacute;dias</a>
				</div>