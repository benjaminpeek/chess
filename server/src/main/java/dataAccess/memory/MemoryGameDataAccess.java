package dataAccess.memory;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.GameDataAccess;
import model.AuthData;
import model.GameData;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MemoryGameDataAccess implements GameDataAccess {
    private final Map<Integer, GameData> gameDataMap = new HashMap<>();
    private int newGameID;
    private final AuthDataAccess authDataAccess;

    public MemoryGameDataAccess(AuthDataAccess authDataAccess) {
        this.authDataAccess = authDataAccess;
    }


    @Override
    public Collection<GameData> listGames() {
        // turn all games from data access to a GameData, and return them in a collection
        HashSet<GameData> allGames = new HashSet<>();
        for (GameData gameData : this.gameDataMap.values()) {
            allGames.add(new GameData(gameData.gameID(), gameData.whiteUsername(),
                    gameData.blackUsername(), gameData.gameName(), gameData.game()));
        }

        return allGames;
    }

    @Override
    public int createGame(String gameName) {
        incrementNewGameID();
        gameDataMap.put(this.newGameID, new GameData(this.newGameID, null, null, gameName,
                new ChessGame()));
        return this.newGameID;
    }

    @Override
    public GameData getGame(int gameID) {
        return this.gameDataMap.get(gameID);
    }

    @Override
    public void addPlayer(String clientColor, int gameID, String authToken) throws DataAccessException {
        GameData game = this.gameDataMap.get(gameID);
        AuthData user = this.authDataAccess.getAuth(authToken);
        if (clientColor.equals("WHITE")) {
            this.gameDataMap.put(gameID, new GameData(gameID, user.username(), game.blackUsername(), game.gameName(),
                    game.game()));
        } else if (clientColor.equals("BLACK")) {
            this.gameDataMap.put(gameID, new GameData(gameID, game.whiteUsername(), user.username(), game.gameName(),
                    game.game()));
        }
    }

    @Override
    public void removePlayer(String clientColor, int gameID, String authToken) {
        GameData gameData = gameDataMap.get(gameID);
        if (clientColor.equals("WHITE")) {
            gameDataMap.put(gameID, new GameData(gameID, null, gameData.blackUsername(),
                    gameData.gameName(), gameData.game()));
        }
        else if (clientColor.equals("BLACK")) {
            gameDataMap.put(gameID, new GameData(gameID, gameData.whiteUsername(), null,
                    gameData.gameName(), gameData.game()));
        }
    }

    @Override
    public void clearGames() {
        gameDataMap.clear();
        this.newGameID = 0;
    }

    @Override
    public void updateGame(int gameID, String whiteAuth, String blackAuth, ChessMove move) throws DataAccessException, InvalidMoveException {
        ChessGame chessGame = getGame(gameID).game();
        if (move != null) {
            chessGame.makeMove(move);
        }
        if (whiteAuth != null) {
            addPlayer("WHITE", gameID, whiteAuth);
        }
        if (blackAuth != null) {
            addPlayer("BLACK", gameID, blackAuth);
        }
    }

    private void incrementNewGameID() {
        this.newGameID++;
    }
}
