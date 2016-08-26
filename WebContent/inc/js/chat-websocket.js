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
	if(message.type === "messageHistory"){
		console.log("messageHistory");
		console.log(message.messageList);
		var tchatHistoryList = message.messageList;
		for(var ii=0;ii<tchatHistoryList.length;ii++){
			var li = document.createElement("li");
			li.className = "collection-item avatar";
			var span = document.createElement("span");
			span.innerHTML = tchatHistoryList[ii];
			if (tchatHistoryList[ii] === 'Vous devez vous connecter pour pouvoir utiliser le chat')
				span.style.color = 'red';
			li.appendChild(span);
			document.getElementById("chatMessageContainer").appendChild(li);
			document.getElementById("ChatContent").scrollTop = document
			.getElementById("ChatContent").scrollHeight;
		}
	}else if(message.type === "message"){
		console.log("message")
		var li = document.createElement("li");
		li.className = "collection-item avatar";
		var span = document.createElement("span");
		span.innerHTML = message.value;
		if (message.value === 'Vous devez vous connecter pour pouvoir utiliser le chat')
			span.style.color = 'red';
		li.appendChild(span);
		console.log(document.getElementById("chatMessageContainer"));
		document.getElementById("chatMessageContainer").appendChild(li);
		document.getElementById("ChatContent").scrollTop = document
				.getElementById("ChatContent").scrollHeight;
	}
}