<%@ tag language="java" pageEncoding="ISO-8859-1"%>
	<div id="chat">
		<div id="ChatContent"
			style="width: 500px; height: 200px; border: 1px solid black; overflow-y: scroll;">
			<ul class="collection" id="chatMessageContainer">
			</ul>
		</div>
		<div class="input-field col s6" style="width: 500px;">
			<input id="messageInput" style="width: 500px;" type="text"
				class="validate" placeholder="Entrez votre message...">
		</div>
	</div>
	<script type="text/javascript" src="inc/js/chat-websocket.js"></script>
