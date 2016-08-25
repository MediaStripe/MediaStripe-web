package com.imie.websockets;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.imie.entities.Utilisateur;
import com.sun.net.httpserver.HttpsConfigurator;

@ServerEndpoint(value = "/testSocket",configurator = GetHttpSessionConfigurator.class)
public class ChatEndPoint {

	private Map<String,ChatUser> mapChatUsers;
	private static CopyOnWriteArrayList<Session> userSessions;
	

	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config){
		HttpSession userSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		System.out.println(userSession.getAttribute("utilisateur"));
		if(userSession.getAttribute("utilisateur")!= null){
			Utilisateur user = (Utilisateur)userSession.getAttribute("utilisateur");
			session.getUserProperties().put("pseudo", user.getPrenom()+" "+user.getNom());
		}
		
		if(this.userSessions == null){
			userSessions = new CopyOnWriteArrayList<Session>();
			
		}
		userSessions.add(session);
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
				session.getBasicRemote().sendText(s.getUserProperties().get("pseudo") +" : "+message);
			}
		}
		//System.out.println("message : " + message);
		
	}
	
	@OnClose
	public void removeSession(Session s){
		String pseudo = (String)s.getUserProperties().get("pseudo");
		userSessions.remove(s);
		/*for(Session session : userSessions){
				try {
					session.getBasicRemote().sendText(pseudo+" a quitté le tchat");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			
		}*/
		
	}
}
