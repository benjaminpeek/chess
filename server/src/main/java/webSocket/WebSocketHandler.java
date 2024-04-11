package webSocket;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.GameDataAccess;
import dataAccess.interfaces.UserDataAccess;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.*;
import webSocketMessages.userCommands.*;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    WebSocketSessions webSocketSessions;
    AuthDataAccess authDataAccess;
    GameDataAccess gameDataAccess;
    UserDataAccess userDataAccess;

    WebSocketHandler(WebSocketSessions webSocketSessions, AuthDataAccess authDataAccess, GameDataAccess gameDataAccess,
                     UserDataAccess userDataAccess) {
        this.webSocketSessions = webSocketSessions;
        this.authDataAccess = authDataAccess;
        this.gameDataAccess = gameDataAccess;
        this.userDataAccess = userDataAccess;
    }


    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(session, message);
            case JOIN_OBSERVER -> joinObserver(session, message);
            case MAKE_MOVE -> makeMove(message);
            case LEAVE -> leaveGame(message);
            case RESIGN -> resignGame(message);
        }
    }

    public void joinPlayer(Session session, String message) throws IOException, DataAccessException {
        JoinPlayer joinPlayerCommand = new Gson().fromJson(message, JoinPlayer.class);
        int gameID = joinPlayerCommand.gameID();
        String authToken = joinPlayerCommand.getAuthString();
        if (authDataAccess.getAuth(authToken) == null) {
            // auth is not valid, so where do i send the error? do i need to account for this here?
        }
        if (gameDataAccess.getGame(gameID) == null) {

        }

        GameData game = gameDataAccess.getGame(gameID);
        webSocketSessions.addSessionToGame(gameID, authToken, session);
        webSocketSessions.sendMessage(gameID, new LoadGame(game), authToken);
        webSocketSessions.broadcastMessage(gameID, new Notification(authDataAccess.getAuth(authToken).username()
                + " joined as " + joinPlayerCommand.playerColor()), authToken);
    }

    public void joinObserver(Session session, String message) {
        JoinObserver joinObserverCommand = new Gson().fromJson(message, JoinObserver.class);
        webSocketSessions.addSessionToGame(joinObserverCommand.gameID(), joinObserverCommand.getAuthString(), session);
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


    // send message/error to root client if gameID/auth is invalid???
    // how would I handle sending the messages to the root client without gameID/session
    // how to get tests to work
    // throw exceptions or send error message?
    // how to broadcast to all users including root client -- does my solution look/work?
    // how to handle observers? where to store them / do I need to?
    // do i use the other service classes in this one to actually add players to the game as necessary? or just the daos
}
