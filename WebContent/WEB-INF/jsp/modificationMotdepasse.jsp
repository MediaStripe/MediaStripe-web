<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
<t:controleUtilisateurConnecte />
		<fieldset>
			<legend>Modification du mot de passe :</legend>
			<form action="./ModificationMotdepasse" method="post" accept-charset="UTF-8">
				<table>
					<tr>
						<td>
							<label for="actuelMotdepasse">
								Mot de passe actuel :
							</label>
						</td>
						<td>
							<input type="password" name="actuelMotdepasse" id="actuelMotdepasse" placeholder="Votre mot de passe actuel" required/>
							<span class="erreur">${ form.listeErreurs['actuelMotdepasse'] }</span>
						</td>
					</tr>
					<tr>
						<td>
							<label for="nouveauMotdepasse">
								Nouveau mot de passe :
							</label>
						</td>
						<td>
							<input type="password" name="nouveauMotdepasse" id="nouveauMotdepasse" placeholder="Votre nouveau mot de passe" required/>
							<span class="erreur">${ form.listeErreurs['nouveauMotdepasse'] }</span>
						</td>
					</tr>
					<tr>
						<td>
							<label for="verificationMotdepasse">
								Retapez votre nouveau mot passe :
							</label>
						</td>
						<td>
							<input type="password" name="verificationMotdepasse" id="verificationMotdepasse" placeholder="Re-saisissez votre mot de passe" required/>
							<span class="erreur">${ form.listeErreurs['verificationMotdepasse'] }</span>
						</td>
					</tr>
				</table>
				
				<input type="submit" value="Modifier"/>
				<span class="erreur">${ form.listeErreurs['result'] }</span>
				<p class="info">${ form.getResultat() }</p>
			</form>
		</fieldset>
</t:genericpage>