<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<fieldset>
			<legend>Mes infos personnelles :</legend>
			<form action="./ModificationUtilisateur" method="post">
				<label for="nom">
					Nom :
				</label>&nbsp;
				<input type="text" name="nom" id="nom" value="<c:out value="${ sessionScope.utilisateur.nom }"/>" placeholder="Votre nom" required/>
				<span class="erreur">${ form.listeErreurs['nom'] }</span><br />

				
				<label for="prenom">
					Pr&eacute;nom :
				</label>&nbsp;
				<input type="text" name="prenom" id="prenom" value="<c:out value="${ sessionScope.utilisateur.prenom }"/>" placeholder="Votre pr&eacute;nom" required/>
				<span class="erreur">${ form.listeErreurs['prenom'] }</span><br />
				
				<label for="mail">
					Adresse mail :
				</label>&nbsp;
				<input type="mail" name="mail" id="mail" value="<c:out value="${ sessionScope.utilisateur.mail }"/>" placeholder="Votre adresse mail" required/>
				<span class="erreur">${ form.listeErreurs['mail'] }</span><br />
				
				<input type="submit" value="Modifier"/>
				<span class="erreur">${ form.listeErreurs['result'] }</span>
				<p class="info">${ form.getResultat() }</p>
			</form>
		</fieldset>
</t:genericpage>