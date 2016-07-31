<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<fieldset>
			<legend>Mes infos personnelles :</legend>
			<form action="./ModificationUtilisateur" method="post">
				<label for="nom">
					Nom :
				</label>&nbsp;
				<input type="text" name="nom" value="<c:out value="${ sessionScope.utilisateur.nom }"/>" placeholder="Votre nom" required/><br/>
				<label for="prenom">
					Pr&eacute;nom :
				</label>&nbsp;
				<input type="text" name="prenom" value="<c:out value="${ sessionScope.utilisateur.prenom }"/>" placeholder="Votre pr&eacute;nom" required/><br/>
				<label for="mail">
					Adresse mail :
				</label>&nbsp;
				<input type="mail" name="mail" value="<c:out value="${ sessionScope.utilisateur.mail }"/>" placeholder="Votre adresse mail" required/><br/>
				<input type="submit" value="Modifier"/>
			</form>
		</fieldset>
</t:genericpage>