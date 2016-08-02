<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<fieldset>
			<legend>Modification du mot de passe :</legend>
			<form action="./ModificationMotdepasse" method="post">
				<label for="actuelMotdepasse">
					Mot de passe actuel :
				</label>&nbsp;
				<input type="password" name="actuelMotdepasse" placeholder="Votre mot de passe actuel" required/>
				<span class="erreur">${ form.listeErreurs['actuelMotdepasse'] }</span><br />
				
				<label for="nouveauMotdepasse">
					Nouveau mot de passe :
				</label>&nbsp;
				<input type="password" name="nouveauMotdepasse" placeholder="Votre nouveau mot de passe" required/>
				<span class="erreur">${ form.listeErreurs['nouveauMotdepasse'] }</span><br />
				
				<label for="verificationMotdepasse">
					Retapez votre nouveau mot passe :
				</label>&nbsp;
				<input type="password" name="verificationMotdepasse" placeholder="Re-saisissez votre mot de passe" required/>
				<span class="erreur">${ form.listeErreurs['verificationMotdepasse'] }</span><br />
				
				<input type="submit" value="Modifier"/>
				<span class="erreur">${ form.listeErreurs['result'] }</span>
				<p class="info">${ form.getResultat() }</p>
			</form>
		</fieldset>
</t:genericpage>