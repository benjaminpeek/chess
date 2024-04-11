package webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.Map;

@WebSocket
public class WebSocketHandler {
    WebSocketSessions webSocketSessions;

    WebSocketHandler(WebSocketSessions webSocketSessions) {
        this.webSocketSessions = webSocketSessions;
    }


    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(session, message);
            case JOIN_OBSERVER -> joinObserver(session, message);
            case MAKE_MOVE -> makeMove(message);
            case LEAVE -> leaveGame(message);
            case RESIGN -> resignGame(message);
        }
    }

    public void joinPlayer(Session session, String message) {
        JoinPlayer joinPlayerCommand = new Gson().fromJson(message, JoinPlayer.class);

    }

    public void joinObserver(Session session, String message) {
        JoinObserver joinObserverCommand = new Gson().fromJson(message, JoinObserver.class);
    }

    public void makeMove(String message) {
        MakeMove makeMoveCommand = new Gson().fromJson(message, MakeMove.class);
    }

    public void leaveGame(String message) {
        LeaveGame leaveGameCommand = new Gson().fromJson(message, LeaveGame.class);
    }

    public void resignGame(String message) {
        ResignGame resignGameCommand = new Gson().fromJson(message, ResignGame.class);
    }

//    public void sendMessage(int gameID, ServerMessage message, String authToken) {
//
//    }
//
//    public void broadcastMessage(int gameID, ServerMessage message, String exceptThisAuth) throws IOException {
////        switch (message.getServerMessageType()) {
////            case NOTIFICATION -> message = new Gson().fromJson(message.toString(), Notification.class);
////            case LOAD_GAME -> message = new Gson().fromJson(message.toString(), LoadGame.class);
////            case ERROR -> message = new Gson().fromJson(message.toString(), Error.class);
////        }
//
//        Map<String, Session> relevantSessions = webSocketSessions.getSessionsForGame(gameID);
//        for (String authToken : relevantSessions.keySet()) {
//            if (!authToken.equals(exceptThisAuth)) {
//                relevantSessions.get(authToken).getRemote().sendString(message.toString());
//            }
//        }
//
//    }

}
