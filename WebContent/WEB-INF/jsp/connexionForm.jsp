<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<form action="./Connexion" method="post">
			<label for="mail">
				Adresse mail<span class="required">*</span> :
			</label>&nbsp;
			<input type="text" name="mail" value="<c:out value="${ utilisateur.mail }" />" required/><br/>
			
			<label for="password">
				Mot de passe<span class="required">*</span> :
			</label>&nbsp;
			<input type="password" name="password" required/><br/>
			
			<input type="submit" value="Connexion"/><br/>
			
			<span class="erreur">${ form.listeErreurs['result'] }</span>
		</form>
	</div>
</body>
</html>