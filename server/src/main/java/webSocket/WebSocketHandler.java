package webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.userCommands.*;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    WebSocketSessions webSocketSessions;

    WebSocketHandler(WebSocketSessions webSocketSessions) {
        this.webSocketSessions = webSocketSessions;
    }


    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(session, message);
            case JOIN_OBSERVER -> joinObserver(session, message);
            case MAKE_MOVE -> makeMove(message);
            case LEAVE -> leaveGame(message);
            case RESIGN -> resignGame(message);
        }
    }

    public void joinPlayer(Session session, String message) throws IOException {
        JoinPlayer joinPlayerCommand = new Gson().fromJson(message, JoinPlayer.class);
        webSocketSessions.addSessionToGame(joinPlayerCommand.gameID(), joinPlayerCommand.getAuthString(), session);
        webSocketSessions.sendMessage(joinPlayerCommand.gameID(), new Gson().fromJson(message, LoadGame.class),
                joinPlayerCommand.getAuthString());
        //
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

}
