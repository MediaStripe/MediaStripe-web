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

@ServerEndpoint(value = "/testSocket",configurator = GetHttpSessionConfigurator.class)
public class ChatEndPoint {

	private static CopyOnWriteArrayList<Session> userSessions;
	private static Map<String, HttpSession> userHttpSessions;

	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config){
		if(ChatEndPoint.userSessions == null)
			userSessions = new CopyOnWriteArrayList<Session>();
		if(ChatEndPoint.userHttpSessions == null)
			userHttpSessions = new HashMap<String,HttpSession>();
		
		
		Utilisateur user = null;
		HttpSession userSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		System.out.println(userSession.getAttribute("utilisateur"));
		if(userSession.getAttribute("utilisateur")!= null){
			user = (Utilisateur)userSession.getAttribute("utilisateur");
			session.getUserProperties().put("userId", user.getId().toString());
			List<Utilisateur> contacts = user.getContacts();
			
			ArrayList<Boolean> contactsConnecte = new ArrayList<Boolean>();
			for(Utilisateur contact: contacts){
				Boolean connecte = true;
				if(userHttpSessions.get(contact.getId().toString())== null)
					connecte = false;
				contactsConnecte.add(connecte);
			}
			session.getUserProperties().put("pseudo", user.getPrenom()+" "+user.getNom());
			JsonObject json = new JsonObject();
			json.addProperty("type", "friendList");
			json.add("friends", new Gson().toJsonTree(contacts).getAsJsonArray());
			json.add("userStatusConnected",new Gson().toJsonTree(contactsConnecte).getAsJsonArray());
			try {
				session.getBasicRemote().sendText(json.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		userSessions.add(session);
		if(user != null)
			userHttpSessions.put(user.getId().toString(),userSession);
		
		
		try {
			if(userSession.getAttribute("tchatSessionHistory") != null){
				@SuppressWarnings("unchecked")
				ArrayList<String>messageHistory = (ArrayList<String>)userSession.getAttribute("tchatSessionHistory");
				JsonObject json = new JsonObject();
				json.addProperty("type", "messageHistory");
				json.add("messageList", new Gson().toJsonTree(messageHistory).getAsJsonArray());
				session.getBasicRemote().sendText(json.toString());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Utilisateurs connectés :" + userSessions.size());
	}
	
	@OnMessage
	public void handleMessage(String message,Session s) throws IOException{
		System.out.println(s.getUserProperties().get("utilisateur"));
		if(!s.getUserProperties().containsKey("pseudo")){
			for(Session session:userSessions){
				if(s.equals(session)){
					session.getBasicRemote().sendText("Vous devez vous connecter pour pouvoir utiliser le chat");
				}
			}
		}else{
			for(Session session:userSessions){
				//Creation et envoi du json contenant le message
				JsonObject json = new JsonObject();
				json.addProperty("type", "message");
				json.addProperty("value",s.getUserProperties().get("pseudo") +" : "+message);
				session.getBasicRemote().sendText(json.toString());
				
				//Ajout du message dans l'historique de la session
				HttpSession httpSession = userHttpSessions.get(s.getUserProperties().get("userId"));
				if(httpSession.getAttribute("tchatSessionHistory") == null)
					httpSession.setAttribute("tchatSessionHistory", new ArrayList<String>());
				@SuppressWarnings("unchecked")
				ArrayList<String> messageHistoryList = (ArrayList<String>)httpSession.getAttribute("tchatSessionHistory");
				messageHistoryList.add(s.getUserProperties().get("pseudo") +" : "+message);
				
			}
		}		
	}
	
	@OnClose
	public void removeSession(Session s){
		userHttpSessions.remove(s.getUserProperties().get("userId"));
		System.out.println(userHttpSessions.size());
		userSessions.remove(s);
		
	}
}
