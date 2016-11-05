<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<fieldset>
			<legend>Inscription :</legend>
			<form action="./Inscription" method="post" accept-charset="UTF-8">
				<table>
					<tr>
						<td>
							<label for="nom">
								Nom<span class="required">*</span> :
							</label>
						</td>
						<td>
							<input type="text" name="nom" id="nom" value="<c:out value="${ utilisateur.nom }" />" placeholder="Votre nom" required />
							<span class="erreur">${ form.listeErreurs['nom'] }</span>
						</td>
					</tr>
					<tr>
						<td>
							<label for="prenom">
								Pr&eacute;nom<span class="required">*</span> :
							</label>
						</td>
						<td>
							<input type="text" name="prenom" id="prenom" value="<c:out value="${ utilisateur.prenom }" />" placeholder="Votre pr&eacute;nom" required />
							<span class="erreur">${ form.listeErreurs['prenom'] }</span>
						</td>
					</tr>
					<tr>
						<td>
							<label for="mail">
								Adresse email<span class="required">*</span> :
							</label>
						</td>
						<td>
							<input type="email" name="mail" id="mail" value="<c:out value="${ utilisateur.mail }" />" placeholder="Votre adresse mail" required />
							<span class="erreur">${ form.listeErreurs['mail'] }</span>
						</td>
					</tr>
					<tr>
						<td>
							<label for="password">
								Mot de passe<span class="required">*</span> :
							</label>
						</td>
						<td>
							<input type="password" name="password" id="password" placeholder="Votre mot de passe" required />
							<span class="erreur">${ form.listeErreurs['password'] }</span>
						</td>
					</tr>
					<tr>
						<td>
							<label for="verificationMotdepasse">
								Retapez votre mot passe :
							</label>
						</td>
						<td>
							<input type="password" name="verificationMotdepasse" id="verificationMotdepasse" placeholder="Re-saisissez votre mot de passe" required/>
							<span class="erreur">${ form.listeErreurs['verificationMotdepasse'] }</span>
						</td>
					</tr>
				</table>
				<input type="reset" value="Effacer"/>
				<input type="submit" value="Valider" /><br/>
				<span class="erreur">${ form.listeErreurs['result'] }</span>
				<p class="info">${ form.getResultat() }</p>
			</form>
		</fieldset>
</t:genericpage>