package webSocket;

import org.eclipse.jetty.websocket.api.Session;

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

    public void removeSession(Session session) {

    }

    public Map<String, Session> getSessionsForGame(int gameID) {
        return sessionsMap.get(gameID);
    }
}
