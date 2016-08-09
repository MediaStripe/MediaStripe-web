<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<fieldset>
			<legend>Inscription :</legend>
			<form action="./Inscription" method="post">
				<label for="nom">
					Nom<span class="required">*</span> :
				</label>&nbsp;
				<input type="text" name="nom" id="nom" value="<c:out value="${ utilisateur.nom }" />" required />
				<span class="erreur">${ form.listeErreurs['nom'] }</span><br />
				
				<label for="prenom">
					Pr&eacute;nom<span class="required">*</span> :
				</label>&nbsp;
				<input type="text" name="prenom" id="prenom" value="<c:out value="${ utilisateur.prenom }" />" required />
				<span class="erreur">${ form.listeErreurs['prenom'] }</span><br />
				
				<label for="mail">
					Adresse email<span class="required">*</span> :
				</label>&nbsp;
				<input type="mail" name="mail" id="mail" value="<c:out value="${ utilisateur.mail }" />" required />
				<span class="erreur">${ form.listeErreurs['mail'] }</span><br />
				
				<label for="password">Mot de passe<span class="required">*</span> :
				</label>&nbsp;
				<input type="password" name="password" id="password" placeholder="Votre mot de passe" required />
				<span class="erreur">${ form.listeErreurs['password'] }</span><br />
				
				<label for="verificationMotdepasse">
					Retapez votre mot passe :
				</label>&nbsp;
				<input type="password" name="verificationMotdepasse" id="verificationMotdepasse" placeholder="Re-saisissez votre mot de passe" required/>
				<span class="erreur">${ form.listeErreurs['verificationMotdepasse'] }</span><br />
				
				<input type="reset" value="Effacer"/>&nbsp;
				<input type="submit" value="Valider" /><br/>
				<span class="erreur">${ form.listeErreurs['result'] }</span>
				<p class="info">${ form.getResultat() }</p>
			</form>
		</fieldset>
</t:genericpage>