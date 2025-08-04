package com.hackathon.broadcastserver.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketMessageHandler extends TextWebSocketHandler {

    private final Map<WebSocketSession, String> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        UriComponents uriComponents = UriComponentsBuilder.fromUri(session.getUri()).build();
        String userName = uriComponents.getQueryParams().getFirst("name");

        if (userName == null || userName.trim().isEmpty() || sessions.containsValue(userName)) {
            if(sessions.containsValue(userName)) {
                session.sendMessage(new TextMessage("Error: 'Name' already exists."));
            }
            else {
                session.sendMessage(new TextMessage("Error: A 'name' query parameter is required to connect."));
            }
            session.close(CloseStatus.BAD_DATA.withReason("Username is required"));
            return;
        }
        else {
            sessions.put(session, userName);
            String welcomeMessage = "Welcome " + userName + "!";
            session.sendMessage(new TextMessage(welcomeMessage));
            broadcastMessage("'" + userName + "' has joined the chat!");
        }
//
//        System.out.println("userName: " + userName);
//        System.out.println("session: " + session);
//        System.out.println("keys: " + sessions.keySet());
//        System.out.println("values: " + sessions.values());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userName = sessions.get(session);
        if (userName != null) {
            broadcastMessage(userName + ": " + message.getPayload());
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userName = sessions.remove(session);
        if (userName != null) {
            broadcastMessage("'" + userName + "' has left the chat.");
        }
    }

    private void broadcastMessage(String message) throws IOException {
        TextMessage textMessage = new TextMessage(message);

        for (WebSocketSession s : sessions.keySet()) {
            if (s.isOpen()) {
                s.sendMessage(textMessage);
            }
        }
    }

    public Map<WebSocketSession, String> getActiveUsersWithNames() {
        return sessions;
    }

    public void stopServer() throws IOException {
        for(WebSocketSession s : sessions.keySet()) {
            if(s.isOpen()) {
                s.close();
            }
        }
        sessions.clear();
    }
}
