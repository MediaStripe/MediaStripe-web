package com.imie.websockets;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.imie.entities.Utilisateur;

@ServerEndpoint(value = "/testSocket", configurator = GetHttpSessionConfigurator.class)
public class ChatEndPoint {

	private static CopyOnWriteArrayList<Session> userSessions;
	private static Map<String, HttpSession> userHttpSessions;

	@OnOpen
	public void onOpen(Session s, EndpointConfig config) {
		if (ChatEndPoint.userSessions == null)
			userSessions = new CopyOnWriteArrayList<Session>();
		if (ChatEndPoint.userHttpSessions == null)
			userHttpSessions = new HashMap<String, HttpSession>();

		Utilisateur user = null;
		HttpSession userSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		System.out.println(userSession.getAttribute("utilisateur"));
		if (userSession.getAttribute("utilisateur") != null) {
			user = (Utilisateur) userSession.getAttribute("utilisateur");
			s.getUserProperties().put("userId", user.getId().toString());
			List<Utilisateur> contacts = user.getContacts();

			ArrayList<Boolean> contactsConnecte = new ArrayList<Boolean>();
			for (Utilisateur contact : contacts) {
				Boolean connecte = true;
				if (userHttpSessions.get(contact.getId().toString()) == null)
					connecte = false;
				contactsConnecte.add(connecte);
			}
			s.getUserProperties().put("pseudo", user.getPrenom() + " " + user.getNom());
			JsonObject json = new JsonObject();
			json.addProperty("type", "friendList");

			json.addProperty("friends", getListeContacts(user));
			json.add("userStatusConnected", new Gson().toJsonTree(contactsConnecte).getAsJsonArray());
			try {
				s.getBasicRemote().sendText(json.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		userSessions.add(s);
		if (user != null){
			userHttpSessions.put(user.getId().toString(), userSession);
			
			for (Utilisateur friend : user.getContacts()) {
				if (userHttpSessions.get(friend.getId().toString()) != null) {
					JsonObject json = new JsonObject();
					json.addProperty("type", "friendUpdateStatus");
					json.addProperty("friendId",s.getUserProperties().get("userId").toString());
					json.addProperty("isConnected", true);
					for (Session session : userSessions) {
						if (session.getUserProperties().get("userId").toString().equals(friend.getId().toString())) {
							System.out.println("Friend found");
							try {
								session.getBasicRemote().sendText(json.toString());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}

				}
			}
		}
			

		try {
			if (userSession.getAttribute("tchatSessionHistory") != null) {
				@SuppressWarnings("unchecked")
				ArrayList<String> messageHistory = (ArrayList<String>) userSession.getAttribute("tchatSessionHistory");
				JsonObject json = new JsonObject();
				json.addProperty("type", "messageHistory");
				json.add("messageList", new Gson().toJsonTree(messageHistory).getAsJsonArray());
				s.getBasicRemote().sendText(json.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
		
		System.out.println("Utilisateurs connectés :" + userSessions.size());
	}

	private String getListeContacts(final Utilisateur utilisateur) {
		final StringBuilder listeContact = new StringBuilder();

		// Concatène l'id, le nom et le prénom de tous les contacts afin de
		// créer une chaîne de caractère qui sera retraitée par le JS.
		// Ex:
		// 1!TOTO Titi;2!DUPONT Pierre
		for (int i = 0; i < utilisateur.getContacts().size(); i++) {
			final Utilisateur contact = utilisateur.getContacts().get(i);

			if(i > 0) {
				listeContact.append(";");
			}
			
			listeContact.append(contact.getId()).append("!").append(contact.getNom()).append(" ")
					.append(contact.getPrenom());
		}

		return listeContact.toString();
	}

	@OnMessage
	public void handleMessage(String message, Session s) throws IOException {
		System.out.println(s.getUserProperties().get("utilisateur"));
		if (!s.getUserProperties().containsKey("pseudo")) {
			for (Session session : userSessions) {
				if (s.equals(session)) {
					session.getBasicRemote().sendText("Vous devez vous connecter pour pouvoir utiliser le chat");
				}
			}
		} else {
			for (Session session : userSessions) {
				// Creation et envoi du json contenant le message
				JsonObject json = new JsonObject();
				json.addProperty("type", "message");
				json.addProperty("value", s.getUserProperties().get("pseudo") + " : " + message);
				session.getBasicRemote().sendText(json.toString());

				// Ajout du message dans l'historique de la session
				HttpSession httpSession = userHttpSessions.get(s.getUserProperties().get("userId"));
				if (httpSession.getAttribute("tchatSessionHistory") == null)
					httpSession.setAttribute("tchatSessionHistory", new ArrayList<String>());
				@SuppressWarnings("unchecked")
				ArrayList<String> messageHistoryList = (ArrayList<String>) httpSession
						.getAttribute("tchatSessionHistory");
				messageHistoryList.add(s.getUserProperties().get("pseudo") + " : " + message);

			}
		}
	}

	@OnClose
	public void removeSession(Session s) {
		HttpSession httpSession = userHttpSessions.get(s.getUserProperties().get("userId"));
		Utilisateur user = (Utilisateur) httpSession.getAttribute("utilisateur");
		List<Utilisateur> friends = user.getContacts();
		for (Utilisateur friend : friends) {
			if (userHttpSessions.get(friend.getId().toString()) != null) {
				JsonObject json = new JsonObject();
				json.addProperty("type", "friendUpdateStatus");
				json.addProperty("friendId",s.getUserProperties().get("userId").toString());
				json.addProperty("isConnected", false);
				for (Session session : userSessions) {
					if (session.getUserProperties().get("userId").toString().equals(friend.getId().toString())) {
						System.out.println("Friend found");
						try {
							session.getBasicRemote().sendText(json.toString());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			}
		}

		userHttpSessions.remove(s.getUserProperties().get("userId"));
		System.out.println(userHttpSessions.size());
		userSessions.remove(s);

	}
}
