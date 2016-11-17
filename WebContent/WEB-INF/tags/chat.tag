<%@ tag language="java" pageEncoding="ISO-8859-1"%>
	<div id="chat">
<!-- 	width: 500px; style="height: 200px; border: 1px solid #CCC; overflow-y: scroll;" -->
		<div id="ChatContent">
			<ul class="collection" id="chatMessageContainer">
			</ul>
		</div>
		<div class="input-field col s6" ><!-- style="width: 500px;" -->
			<input id="messageInput" type="text"
				class="validate" placeholder="Entrez votre message...">
		</div>
		<label>Contacts</label><br>
		<ul id="friendsList">
		
		</ul>
	</div>
	
	<script type="text/javascript" src="inc/js/chat-websocket.js"></script>
