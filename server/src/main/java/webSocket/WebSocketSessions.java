package webSocket;

import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebSocketSessions {
    Map<Integer, Map<String, Session>> sessionsMap;

    WebSocketSessions() {
        this.sessionsMap = new HashMap<>();
    }


    public void addSessionToGame(int gameID, String authToken, Session session) {
        sessionsMap.get(gameID).put(authToken, session);
    }

    public void removeSessionFromGame(int gameID, String authToken, Session session) {
        Map<String, Session> game = sessionsMap.get(gameID);
        game.remove(authToken, session);
    }

    public Map<String, Session> getSessionsForGame(int gameID) {
        return sessionsMap.get(gameID);
    }

    public void sendMessage(int gameID, ServerMessage message, String authToken) throws IOException {
        sessionsMap.get(gameID).get(authToken).getRemote().sendString(message.toString());
    }

    public void broadcastMessage(int gameID, ServerMessage message, String exceptThisAuth) throws IOException {
        Map<String, Session> relevantSessions = getSessionsForGame(gameID);
        for (String authToken : relevantSessions.keySet()) {
            if (!authToken.equals(exceptThisAuth)) {
                relevantSessions.get(authToken).getRemote().sendString(message.toString());
            }
        }
    }

    public void broadcastMessageAll(int gameID, ServerMessage message) throws IOException {
        Map<String, Session> relevantSessions = getSessionsForGame(gameID);
        for (String authToken : relevantSessions.keySet()) {
            relevantSessions.get(authToken).getRemote().sendString(message.toString());
        }
    }
}
