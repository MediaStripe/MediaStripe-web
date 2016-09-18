<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:genericpage>
		<fieldset>
			<legend>Connexion :</legend>
			<%-- Si un utilisateur a tenté d'accéder à une page necéssitant une session sans en avoir,
				il sera redirigé sur cette page avec un message d'erreur. --%>
			<c:if test="${ !empty accessDenied }">
				<span class="erreur">${ accessDenied }</span>
			</c:if>
			<span></span>
			<form action="./Connexion" method="post" accept-charset="UTF-8">
				<label for="mail">
					Adresse mail<span class="required">*</span> :
				</label>&nbsp;
				<input type="text" name="mail" id="mail" value="<c:out value="${ mailSaisi }" />" required/><br/>
				
				<label for="password">
					Mot de passe<span class="required">*</span> :
				</label>&nbsp;
				<input type="password" id="password" name="password" required/><br/>
				
				<input type="submit" value="Connexion"/><br/>
				
				<span class="erreur">${ form.listeErreurs['result'] }</span>
			</form>
		</fieldset>
</t:genericpage>