<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<div>
			<form action="./Inscription" method="post">
				<label for="nom">
					Nom :<span class="required">*</span>
				</label>&nbsp;
				<input type="text" name="nom" value="<c:out value="${ utilisateur.nom }" />" required />
				<span class="erreur">${ form.listeErreurs['nom'] }</span><br />
				
				<label for="prenom">
					Pr&eacute;nom :<span class="required">*</span>
				</label>&nbsp;<input type="text" name="prenom" value="<c:out value="${ utilisateur.prenom }" />" required />
				<span class="erreur">${ form.listeErreurs['prenom'] }</span><br />
				
				<label for="mail">
					Adresse email :<span class="required">*</span>
				</label>&nbsp;<input type="mail" name="mail" value="<c:out value="${ utilisateur.mail }" />" required />
				<span class="erreur">${ form.listeErreurs['mail'] }</span><br />
				
				<label for="password">Mot de passe :<span class="required">*</span>
				</label>&nbsp;<input type="password" name="password" required />
				<span class="erreur">${ form.listeErreurs['password'] }</span><br />
				
				<input type="reset" value="Effacer"/>&nbsp;
				<input type="submit" value="Valider" /><br/>
				<span class="erreur">${ form.listeErreurs['result'] }</span>
				<p class="info">${ form.getResultat() }</p>
			</form>
		</div>
	</body>
</html>