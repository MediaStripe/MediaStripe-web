<%@tag description="Template de base du site MediaStripe" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MediaStripe</title>
		<link type="text/css" rel="stylesheet" href="./inc/css/style.css" />
	</head>
	<body>
		<div id="header">
			MediaStripe
		</div>
		<div id="menu">
			<div id="links">
				<a href="./Accueil">Accueil</a><br/>
				<c:choose>
					<c:when test="${ empty sessionScope.utilisateur }">
						<a href="./Inscription">S'inscrire</a><br/>
						<a href="./Connexion"><img src="./inc/images/connexion.png" class="menuIcon" alt="C"/> Connexion</a>
					</c:when>
					<c:otherwise>
						<t:menuUtilisateur />
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div id="content">
			<jsp:doBody />
		</div>
		<div id="chat">
			<div id="ChatContent"
		style="width: 500px; height: 200px; border: 1px solid black; overflow-y: scroll;">
		<ul class="collection" id="chatMessageContainer">
		</ul>
	</div>
<div class="input-field col s6"  style="width:500px;">
    <input id="messageInput" style="width:500px;" type="text" class="validate" placeholder="Entrez votre message...">
</div>
</body>
<script type="text/javascript">
	var connectionChat = new WebSocket("ws://" + location.host
			+ "/MediaStripe-web/testSocket");
	document.getElementById("messageInput").addEventListener("keydown",
			function(e) {
				if (e.keyCode === 13 && this.value !== "") {
					connectionChat.send(this.value);
					this.value = "";
				}
			});
	connectionChat.onmessage = function(event) {
		var li = document.createElement("li");
        li.className= "collection-item avatar";
		//li.innerHTML = event.data;
        /*var img = document.createElement('img');
        img.className = "circle";
        img.setAttribute("src", "images/yuna.jpg");*/
        var span = document.createElement("span");
        span.innerHTML= event.data;
        if(event.data==='Vous devez vous connecter pour pouvoir utiliser le chat')
        	span.style.color="red";
        //li.appendChild(img);
        li.appendChild(span);

		//li.setAttribute("style","color: #7C6F6F; font-size: 70%;margin: 5px;");
		document.getElementById("chatMessageContainer").appendChild(li);
        document.getElementById("ChatContent").scrollTop = document.getElementById("ChatContent").scrollHeight;
        //objDiv.scrollTop = objDiv.scrollHeight;
	}
</script>
		</div>
	

</body>
</html>