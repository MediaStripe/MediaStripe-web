var connectionChat = new WebSocket("ws://" + location.host
		+ "/MediaStripe-web/testSocket");
document.getElementById("messageInput").addEventListener("keydown",
		function(e) {
			if (e.keyCode === 13 && this.value !== "") {
				connectionChat.send(this.value);
				this.value = "";
			}
		});
connectionChat.onmessage = function(event){
	console.log(typeof event.data);
	var message = JSON.parse(event.data);
	if(message.type === 'messageHistory'){
		var tchatHistoryList = message.messageList;
		for(var ii=0;ii<tchatHistoryList.length;ii++){
			displayMessage(tchatHistoryList[ii]);
		}
	}else if(message.type === 'message'){
		displayMessge(message.value);
	}else if(message.type === 'friendList'){
		displayFriends(message.friends, message.userStatusConnected);
	}
}


function displayMessage(message){
	var li = document.createElement("li");
	li.className = "collection-item avatar";
	var span = document.createElement("span");
	span.innerHTML = message;
	if (message === 'Vous devez vous connecter pour pouvoir utiliser le chat')
		span.style.color = 'red';
	li.appendChild(span);
	console.log(document.getElementById("chatMessageContainer"));
	document.getElementById("chatMessageContainer").appendChild(li);
	document.getElementById("ChatContent").scrollTop = document
			.getElementById("ChatContent").scrollHeight;
}


function displayFriends(friends,friendsConnectedStatus){
	var friendsUlElement = document.getElementById("friendsList");
	console.log(friendsConnectedStatus);
	for(var ii=0;ii<friends.length;ii++){
		var li = document.createElement('li');
		li.innerHTML = friends[ii].prenom +' '+friends[ii].nom;
		
		if(friendsConnectedStatus[ii])
			li.innerHTML += '<div class="" style="background: #35ac19;width: 7px;height: 7px;border-radius: 99999px;padding: 0;"></div>';
			//li.innerHTML += ' : Connecté';
		else
			li.innerHTML += ' : Déconnecté';
		li.dataset.userid = friends[ii].id;
		friendsUlElement.appendChild(li);
	}
}