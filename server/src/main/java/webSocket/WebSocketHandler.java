package webSocket;

import chess.ChessGame;
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
import webSocketMessages.serverMessages.Error;
import webSocketMessages.userCommands.*;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    WebSocketSessions webSocketSessions;
    AuthDataAccess authDataAccess;
    GameDataAccess gameDataAccess;
    UserDataAccess userDataAccess;

    public WebSocketHandler(WebSocketSessions webSocketSessions, AuthDataAccess authDataAccess, GameDataAccess gameDataAccess,
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
            sendErrorMessage(session, "invalid auth token");
            return;
        }
        if (gameDataAccess.getGame(gameID) == null) {
            sendErrorMessage(session, "invalid game ID");
            return;
        }
        // valid game and auth, now pull more data to use
        GameData gameData = gameDataAccess.getGame(gameID);
        String username = authDataAccess.getAuth(authToken).username();
        String whiteUsername = gameData.whiteUsername();
        String blackUsername = gameData.blackUsername();
        ChessGame.TeamColor playerColor = joinPlayerCommand.playerColor();

        // verify that this game has, beforehand, been joined by this player
        if (playerColor.equals(ChessGame.TeamColor.WHITE)) {
            if (whiteUsername == null || !whiteUsername.equals(username)) {
                sendErrorMessage(session, "invalid team color");
                return;
            }
        } else if (playerColor.equals(ChessGame.TeamColor.BLACK)) {
            if (blackUsername == null || !blackUsername.equals(username)) {
                sendErrorMessage(session, "invalid team color");
                return;
            }
        }

        webSocketSessions.addSessionToGame(gameID, authToken, session);
        webSocketSessions.sendMessage(gameID, new LoadGame(gameData), authToken);
        webSocketSessions.broadcastMessage(gameID, new Notification(authDataAccess.getAuth(authToken).username()
                + " joined as " + joinPlayerCommand.playerColor()), authToken);
    }

    public void joinObserver(Session session, String message) throws DataAccessException, IOException {
        JoinObserver joinObserverCommand = new Gson().fromJson(message, JoinObserver.class);
        int gameID = joinObserverCommand.gameID();
        String authToken = joinObserverCommand.getAuthString();
        if (authDataAccess.getAuth(authToken) == null) {
            String errorMsg = new Gson().toJson(new Error("invalid auth token"));
            session.getRemote().sendString(errorMsg);
        }
        if (gameDataAccess.getGame(gameID) == null) {
            String errorMsg = new Gson().toJson(new Error("invalid game ID"));
            session.getRemote().sendString(errorMsg);
        }
        GameData gameData = gameDataAccess.getGame(gameID);

        webSocketSessions.addSessionToGame(joinObserverCommand.gameID(), joinObserverCommand.getAuthString(), session);
        webSocketSessions.sendMessage(gameID, new LoadGame(gameData), authToken);
        webSocketSessions.broadcastMessage(gameID, new Notification(authDataAccess.getAuth(authToken).username()
                + " joined as an observer"), authToken);

    }

    public void makeMove(String message) {
        MakeMove makeMoveCommand = new Gson().fromJson(message, MakeMove.class);
        // ChessGame/Move logic here
        // convert letters to numbers
    }

    public void leaveGame(String message) {
        LeaveGame leaveGameCommand = new Gson().fromJson(message, LeaveGame.class);
        // remove the session from game
        // remove username from database?
    }

    public void resignGame(String message) {
        ResignGame resignGameCommand = new Gson().fromJson(message, ResignGame.class);
        // ChessGame/Move logic
        // stop gameplay, still in the session, but now an "observer"
        // remove username from database, but keep them in the session
        //  update in game dao that takes a new GameData, with updated usernames, etc., overwriting the old game

    }

    private void sendErrorMessage(Session session, String message) throws IOException {
        String errorMsg = new Gson().toJson(new Error(message));
        session.getRemote().sendString(errorMsg);
    }


    // send message/error to root client if gameID/auth is invalid???
    // how would I handle sending the messages to the root client without gameID/session
    // how to get tests to work
    // throw exceptions or send error message?
    // how to broadcast to all users including root client -- does my solution look/work?
    // how to handle observers? where to store them / do I need to?
    // do i use the other service classes in this one to actually add players to the game as necessary? or just the daos
    // does all the game login happen in this class? making moves, checking for check/mate, winning/losing?
    // how to handle make move commands, where does the logic go that allows for chess notation to be used to make moves?
    //  probably in ChessMove, as that is what gets passed in to the MAKE_MOVE user command?
}
