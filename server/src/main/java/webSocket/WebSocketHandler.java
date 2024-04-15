package webSocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.GameDataAccess;
import dataAccess.interfaces.UserDataAccess;
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
    public void onMessage(Session session, String message) throws IOException, DataAccessException, InvalidMoveException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(session, message);
            case JOIN_OBSERVER -> joinObserver(session, message);
            case MAKE_MOVE -> makeMove(session, message);
            case LEAVE -> leaveGame(session, message);
            case RESIGN -> resignGame(session, message);
        }
    }

    public void joinPlayer(Session session, String message) throws IOException, DataAccessException {
        JoinPlayer joinPlayerCommand = new Gson().fromJson(message, JoinPlayer.class);
        int gameID = joinPlayerCommand.gameID();
        String authToken = joinPlayerCommand.getAuthString();
        if (badGameID(gameID)) {
            sendErrorMessage(session, "invalid game ID");
            return;
        }
        if (badAuth(authToken)) {
            sendErrorMessage(session, "invalid auth token");
            return;
        }
        // valid game and auth, now pull more data to use
        String username = authDataAccess.getAuth(authToken).username();
        String whiteUsername = gameDataAccess.getGame(gameID).whiteUsername();
        String blackUsername = gameDataAccess.getGame(gameID).blackUsername();
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
        webSocketSessions.sendMessage(gameID, new LoadGame(gameDataAccess.getGame(gameID)), authToken);
        webSocketSessions.broadcastMessage(gameID, new Notification(username + " joined as "
                + playerColor), authToken);
    }

    public void joinObserver(Session session, String message) throws DataAccessException, IOException {
        JoinObserver joinObserverCommand = new Gson().fromJson(message, JoinObserver.class);
        int gameID = joinObserverCommand.gameID();
        String authToken = joinObserverCommand.getAuthString();
        if (badGameID(gameID)) {
            sendErrorMessage(session, "invalid game ID");
            return;
        }
        if (badAuth(authToken)) {
            sendErrorMessage(session, "invalid auth token");
            return;
        }

        webSocketSessions.addSessionToGame(joinObserverCommand.gameID(), joinObserverCommand.getAuthString(), session);
        webSocketSessions.sendMessage(gameID, new LoadGame(gameDataAccess.getGame(gameID)), authToken);
        webSocketSessions.broadcastMessage(gameID, new Notification(authDataAccess.getAuth(authToken).username()
                + " joined as an observer"), authToken);

    }

    public void makeMove(Session session, String message) throws IOException, DataAccessException, InvalidMoveException {
        MakeMove makeMoveCommand = new Gson().fromJson(message, MakeMove.class);
        int gameID = makeMoveCommand.gameID();
        String authToken = makeMoveCommand.getAuthString();
        if (badGameID(gameID)) {
            sendErrorMessage(session, "invalid game ID");
            return;
        }
        if (badAuth(authToken)) {
            sendErrorMessage(session, "invalid auth token");
            return;
        }

        ChessGame chessGame = gameDataAccess.getGame(gameID).game();
        ChessMove move = makeMoveCommand.move();
        String username = authDataAccess.getAuth(authToken).username();
        String whiteUsername = gameDataAccess.getGame(gameID).whiteUsername();
        String blackUsername = gameDataAccess.getGame(gameID).blackUsername();

        if (whiteUsername == null || blackUsername == null) {
            sendErrorMessage(session, "observers cannot make moves");
            return;
        }
        // if user is an observer
        if (!whiteUsername.equals(username) && !blackUsername.equals(username)) {
            sendErrorMessage(session, "observers cannot make moves");
            return;
        }
        // if move is invalid
        else if (!chessGame.validMoves(move.getStartPosition()).contains(move)) {
            sendErrorMessage(session, "move is not valid");
            return;
        }
        // if making move out of turn (requesting color does not match TeamTurn color)
        switch (chessGame.getTeamTurn()) {
            case WHITE -> {
                if (blackUsername.equals(username)) {
                    sendErrorMessage(session, "trying to make move out of turn");
                    return;
                }
            }
            case BLACK -> {
                if (whiteUsername.equals(username)) {
                    sendErrorMessage(session, "trying to make move out of turn");
                    return;
                }
            }
        }
        if (chessGame.isInCheck(ChessGame.TeamColor.WHITE)) {
            webSocketSessions.broadcastMessage(gameID, new Notification(whiteUsername + " is in check! "), authToken);
        }
        if (chessGame.isInCheck(ChessGame.TeamColor.BLACK)) {
            webSocketSessions.broadcastMessage(gameID, new Notification(blackUsername + " is in check! "), authToken);
        }
        if (chessGame.isGameOver()) {
            sendErrorMessage(session, "cannot make moves when a game is over");
            return;
        }

        gameDataAccess.updateGame(gameID, move);
        webSocketSessions.broadcastMessageAll(gameID, new LoadGame(gameDataAccess.getGame(gameID)));
        webSocketSessions.broadcastMessage(gameID, new Notification(authDataAccess.getAuth(authToken).username()
         + " made move " + move), authToken);
    }

    public void leaveGame(Session session, String message) throws DataAccessException, IOException {
        LeaveGame leaveGameCommand = new Gson().fromJson(message, LeaveGame.class);
        int gameID = leaveGameCommand.gameID();
        String authToken = leaveGameCommand.getAuthString();
        if (badGameID(gameID)) {
            sendErrorMessage(session, "invalid game ID");
            return;
        }
        if (badAuth(authToken)) {
            sendErrorMessage(session, "invalid auth token");
            return;
        }

        if (gameDataAccess.getGame(gameID).whiteUsername() != null) {
            if (gameDataAccess.getGame(gameID).whiteUsername().equals(authDataAccess.getAuth(authToken).username())) {
                gameDataAccess.removePlayer("WHITE", gameID, authToken);
            }
        }
        if (gameDataAccess.getGame(gameID).blackUsername() != null) {
            if (gameDataAccess.getGame(gameID).blackUsername().equals(authDataAccess.getAuth(authToken).username())) {
                gameDataAccess.removePlayer("BLACK", gameID, authToken);
            }
        }

        webSocketSessions.removeSessionFromGame(gameID, authToken, session);
        webSocketSessions.broadcastMessage(gameID, new Notification(authDataAccess.getAuth(authToken).username()
                + " left the game "), authToken);
    }

    public void resignGame(Session session, String message) throws DataAccessException, IOException {
        ResignGame resignGameCommand = new Gson().fromJson(message, ResignGame.class);
        int gameID = resignGameCommand.gameID();
        String authToken = resignGameCommand.getAuthString();
        if (badGameID(gameID)) {
            sendErrorMessage(session, "invalid game ID");
            return;
        }
        if (badAuth(authToken)) {
            sendErrorMessage(session, "invalid auth token");
            return;
        }
        if (gameDataAccess.getGame(gameID).whiteUsername() == null
                || gameDataAccess.getGame(gameID).blackUsername() == null) {
            sendErrorMessage(session, "cannot resign against null opponent");
            return;
        }
        if (!gameDataAccess.getGame(gameID).whiteUsername().equals(authDataAccess.getAuth(authToken).username())
                && !gameDataAccess.getGame(gameID).blackUsername().equals(authDataAccess.getAuth(authToken).username())) {
            sendErrorMessage(session, "observers cannot resign");
            return;
        }

        // remove white and black player if they exist
        // update the game to over
        if (gameDataAccess.getGame(gameID).whiteUsername() != null) {
            gameDataAccess.removePlayer("WHITE", gameID, authToken);
        }
        if (gameDataAccess.getGame(gameID).blackUsername() != null) {
            gameDataAccess.removePlayer("BLACK", gameID, authToken);
        }

        gameDataAccess.getGame(gameID).game().setGameOver();
        webSocketSessions.broadcastMessageAll(gameID, new Notification(authDataAccess.getAuth(authToken).username()
                + " has resigned and the game is now over"));
    }

    private void sendErrorMessage(Session session, String message) throws IOException {
        String errorMsg = new Gson().toJson(new Error(message));
        session.getRemote().sendString(errorMsg);
    }

    private boolean badAuth(String authToken) throws DataAccessException {
        return authDataAccess.getAuth(authToken) == null;
    }

    private boolean badGameID(int gameID) throws DataAccessException {
        return gameDataAccess.getGame(gameID) == null;
    }
}
