package webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebSocketSessions {
    Map<Integer, Map<String, Session>> sessionsMap;

    public WebSocketSessions() {
        this.sessionsMap = new HashMap<>();
    }


    public void addSessionToGame(int gameID, String authToken, Session session) {
        Map<String, Session> game = sessionsMap.get(gameID);
        if (game == null) {
            Map<String, Session> newGame = new HashMap<>();
            newGame.put(authToken, session);
            sessionsMap.put(gameID, newGame);
        } else {
            game.put(authToken, session);
            sessionsMap.put(gameID, game);
        }
    }

    public void removeSessionFromGame(int gameID, String authToken, Session session) {
        Map<String, Session> game = sessionsMap.get(gameID);
        game.remove(authToken, session);
    }

    public Map<String, Session> getSessionsForGame(int gameID) {
        return sessionsMap.get(gameID);
    }

    public void sendMessage(int gameID, ServerMessage message, String authToken) throws IOException {
        String messageJSON = new Gson().toJson(message);
        Session session = sessionsMap.get(gameID).get(authToken);
        if(session.isOpen()) {
            session.getRemote().sendString(messageJSON);
        }
    }

    public void broadcastMessage(int gameID, ServerMessage message, String exceptThisAuth) throws IOException {
        String messageJSON = new Gson().toJson(message);
        Map<String, Session> relevantSessions = getSessionsForGame(gameID);
        for (String authToken : relevantSessions.keySet()) {
            Session session = sessionsMap.get(gameID).get(authToken);
            if (session.isOpen()) {
                if (!authToken.equals(exceptThisAuth)) {
                    session.getRemote().sendString(messageJSON);
                }
            }
        }
    }

    public void broadcastMessageAll(int gameID, ServerMessage message) throws IOException {
        String messageJSON = new Gson().toJson(message);
        Map<String, Session> relevantSessions = getSessionsForGame(gameID);
        for (String authToken : relevantSessions.keySet()) {
            relevantSessions.get(authToken).getRemote().sendString(messageJSON);
        }
    }
}
