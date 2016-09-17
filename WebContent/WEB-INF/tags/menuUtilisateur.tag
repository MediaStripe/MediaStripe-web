<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
				<div>
					Gestion du compte :
					<a href="./GestionCompte">
						${ sessionScope.utilisateur.nom } ${ sessionScope.utilisateur.prenom }
					</a><br/>
					<a href="./Deconnexion" ><img src="./inc/images/deconnexion.png" class="menuIcon" alt="D"/> D&eacute;connexion</a><br/>
					Gestion des m&eacute;dias :<br/>
					<a href="./AjoutFichier">Ajouter un m&eacute;dia</a><br/>
					<a href="./ListerMedias">Consulter mes m&eacute;dias</a>
				</div>