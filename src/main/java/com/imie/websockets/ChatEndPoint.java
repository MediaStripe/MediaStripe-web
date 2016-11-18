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

@ServerEndpoint(value = "/tchatSocket", configurator = GetHttpSessionConfigurator.class)
public class ChatEndPoint {

	//Sessions Websocket
	private static CopyOnWriteArrayList<Session> userSessions;
	//Sessions HTTP
	private static Map<String, HttpSession> userHttpSessions;

	@OnOpen
	public void onOpen(Session s, EndpointConfig config) {
		//Initialisation des listes
		if (ChatEndPoint.userSessions == null)
			userSessions = new CopyOnWriteArrayList<Session>();
		if (ChatEndPoint.userHttpSessions == null)
			userHttpSessions = new HashMap<String, HttpSession>();

		
		Utilisateur user = null;
		//Récupération de la session HTTP de l'utilisateur connecté
		HttpSession userSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		userSessions.add(s);
		if (userSession.getAttribute("utilisateur") != null) {
			user = (Utilisateur) userSession.getAttribute("utilisateur");
			userHttpSessions.put(user.getId().toString(), userSession);
			//Ajout de l'id et du Nom Prenom du user dans les propriétés de la session
			s.getUserProperties().put("userId", user.getId().toString());
			s.getUserProperties().put("pseudo", user.getPrenom() + " " + user.getNom());

			//Recherche des contacts connectés
			List<Utilisateur> contacts = user.getContacts();
			ArrayList<Boolean> contactsConnecte = new ArrayList<Boolean>();
			for (Utilisateur contact : contacts) {
				Boolean connecte = true;
				if (userHttpSessions.get(contact.getId().toString()) == null)
					connecte = false;
				contactsConnecte.add(connecte);
			}
			//Envoie du message avec la listes des contacts
			sendFriendList(s, contactsConnecte, user);
			//Envoie le statut connecté au contact de l'utilisateur
			sendContactStatus(s, user);
		}
		sendTchatSessionHistory(s, userSession);
	}

	

	@OnMessage
	public void handleMessage(String message, Session s) throws IOException {
		System.out.println(s.getUserProperties().get("utilisateur"));
		if (s.getUserProperties().containsKey("pseudo")) {
			for (Session session : userSessions) {
				//envoi du json contenant le message
				sendMessage(s, session, message);

				// Ajout du message dans l'historique de la session
				HttpSession httpSession = userHttpSessions.get(s.getUserProperties().get("userId"));
				if (httpSession.getAttribute("tchatSessionHistory") == null)
					httpSession.setAttribute("tchatSessionHistory", new ArrayList<String>());
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
		sendContactStatusDisconnected(s, user.getContacts());

		userHttpSessions.remove(s.getUserProperties().get("userId"));
		System.out.println(userHttpSessions.size());
		userSessions.remove(s);

	}
	/**
	 * 
	 * @param session de l'utilisateur
	 * @param Liste des statut connecté des contacts de l'utilisateur
	 * @param utilisateur courant
	 */
	public  void sendFriendList(Session session,ArrayList<Boolean>contactsConnecte, Utilisateur user){
		JsonObject json = new JsonObject();
		json.addProperty("type", "friendList");
		String contactList = getListeContacts(user);
		if(contactList.length() != 0){
			json.addProperty("friends", getListeContacts(user));
			json.add("userStatusConnected", new Gson().toJsonTree(contactsConnecte).getAsJsonArray());
			try {
				session.getBasicRemote().sendText(json.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private String getListeContacts(final Utilisateur utilisateur) {
		final StringBuilder listeContact = new StringBuilder();

		// ConcatÃ¨ne l'id, le nom et le prÃ©nom de tous les contacts afin de
		// crÃ©er une chaÃ®ne de caractÃ¨re qui sera retraitÃ©e par le JS.
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
	
	public void sendContactStatus(Session s,Utilisateur user){
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
	
	
	public void sendContactStatusDisconnected(Session s,List<Utilisateur>contacts){
		for (Utilisateur contact : contacts) {
			if (userHttpSessions.get(contact.getId().toString()) != null) {
				JsonObject json = new JsonObject();
				json.addProperty("type", "friendUpdateStatus");
				json.addProperty("friendId",s.getUserProperties().get("userId").toString());
				json.addProperty("isConnected", false);
				for (Session session : userSessions) {
					if (session.getUserProperties().get("userId").toString().equals(contact.getId().toString())) {
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
	
	public void sendTchatSessionHistory(Session s,HttpSession userSession){
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
	}
	
	
	public void sendMessage(Session s, Session contactSession,String message){
		JsonObject json = new JsonObject();
		json.addProperty("type", "message");
		json.addProperty("value", s.getUserProperties().get("pseudo") + " : " + message);
		try {
			contactSession.getBasicRemote().sendText(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
