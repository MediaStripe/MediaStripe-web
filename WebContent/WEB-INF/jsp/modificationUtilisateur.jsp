<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
<t:controleUtilisateurConnecte />
		<fieldset>
			<legend>Mes infos personnelles :</legend>
			<form action="./ModificationUtilisateur" method="post" accept-charset="UTF-8">
				<table>
					<tr>
						<td>
							<label for="nom">
								Nom :
							</label>
						</td>
						<td>
							<input type="text" name="nom" id="nom" value="<c:out value="${ sessionScope.utilisateur.nom }"/>" placeholder="Votre nom" required/>
							<span class="erreur">${ form.listeErreurs['nom'] }</span>
						</td>
					</tr>
					<tr>
						<td>
							<label for="prenom">
								Pr&eacute;nom :
							</label>
						</td>
						<td>
							<input type="text" name="prenom" id="prenom" value="<c:out value="${ sessionScope.utilisateur.prenom }"/>" placeholder="Votre pr&eacute;nom" required/>
							<span class="erreur">${ form.listeErreurs['prenom'] }</span>
						</td>
					</tr>
					<tr>
						<td>
							<label for="mail">
								Adresse mail :
							</label>
						</td>
						<td>
							<input type="email" name="mail" id="mail" value="<c:out value="${ sessionScope.utilisateur.mail }"/>" placeholder="Votre adresse mail" required/>
							<span class="erreur">${ form.listeErreurs['mail'] }</span>
						</td>
					</tr>
				</table>
				
				<input type="submit" value="Modifier"/>
				<span class="erreur">${ form.listeErreurs['result'] }</span>
				<p class="info">${ form.getResultat() }</p>
			</form>
		</fieldset>
</t:genericpage>