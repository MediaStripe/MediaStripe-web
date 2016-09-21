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
	var message = JSON.parse(event.data);
	if (message.type === 'messageHistory') {
		var tchatHistoryList = message.messageList;
		for (var ii = 0; ii < tchatHistoryList.length; ii++) {
			displayMessage(tchatHistoryList[ii]);
		}
	} else if (message.type === 'message') {
		displayMessage(message.value);
	} else if (message.type === 'friendList') {
		var listeFriends = message.friends.split(';');
		displayFriends(listeFriends, message.userStatusConnected);
	} else if(message.type === 'friendUpdateStatus')
		updateStatus(message.friendId,message.isConnected);
	
}

function updateStatus(friendId,isConnected) {
	console.log(typeof isConnected);
	if(isConnected){
		document.getElementById('chatFriend'+friendId).firstChild.style.backgroundColor ='green';
		
	}else
		document.getElementById('chatFriend'+friendId).firstChild.style.backgroundColor ='red';
		
}
function displayMessage(message) {
	var li = document.createElement("li");
	li.className = "collection-item avatar";
	var span = document.createElement("span");
	span.innerHTML = message;
	if (message === 'Vous devez vous connecter pour pouvoir utiliser le chat')
		span.style.color = 'red';
	li.appendChild(span);
	document.getElementById("chatMessageContainer").appendChild(li);
	document.getElementById("ChatContent").scrollTop = document
			.getElementById("ChatContent").scrollHeight;
}

/**
 * Affiche la liste des contacts de l'utilisateur connecté.
 * 
 * @param friends
 *            La liste des contacts, sous forme de tableau de chaînes au format
 *            "id!nom prenom".
 * @param friendsConnectedStatus
 *            Liste de booleens indiquant pour chaque utilisateur s'il est
 *            connecté ou non.
 */
function displayFriends(friends, friendsConnectedStatus) {
	var friendsUlElement = document.getElementById("friendsList");


	for (var ii = 0; ii < friends.length; ii++) {
		var li = document.createElement('li');

		// Création du point de couleur indiquant si le contact est connecté.
		li.innerHTML = '<div class="" style="display:inline-block;background: #'
				+ (friendsConnectedStatus[ii] ? '35ac19' : 'F00')
				+ ';width: 7px;height: 7px;border-radius: 99999px;padding: 0;" /> ';

		// Récupération du contact
		var ami = friends[ii].split('!');
		li.innerHTML += getNomEtPrenomContact(ami);
		li.id = 'chatFriend'+getIdContact(ami);
		li.dataset.userid = getIdContact(ami);
		friendsUlElement.appendChild(li);
	}
}

/**
 * Retourne l'identifiant du contact passé en paramètre.
 * 
 * @param friendSplited
 *            L'ami passé en paramètre, en tant que chaîne, sous la forme :
 *            "id!nom prenom"
 * @returns L'id du contact
 */
function getIdContact(contact) {
	return contact[0];
}

/**
 * Retourne le nom et le prénom du contact passé en paramètre.
 * 
 * @param friendSplited
 *            L'ami passé en paramètre, en tant que chaîne, sous la forme :
 *            "id!nom prenom"
 * @returns Le nom et le prénom du contact. Ex: "DUPONT Pierre"
 */
function getNomEtPrenomContact(contact) {
	return contact[1];
}