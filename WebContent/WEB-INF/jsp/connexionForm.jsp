<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<div>
			<form action="./Connexion" method="post">
				<label for="mail">
					Adresse mail<span class="required">*</span> :
				</label>&nbsp;
				<input type="text" name="mail" value="<c:out value="${ mailSaisi }" />" required/><br/>
				
				<label for="password">
					Mot de passe<span class="required">*</span> :
				</label>&nbsp;
				<input type="password" name="password" required/><br/>
				
				<input type="submit" value="Connexion"/><br/>
				
				<span class="erreur">${ form.listeErreurs['result'] }</span>
			</form>
		</div>
</t:genericpage>