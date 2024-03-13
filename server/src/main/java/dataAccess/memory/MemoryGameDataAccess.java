package dataAccess.memory;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.GameDataAccess;
import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MemoryGameDataAccess implements GameDataAccess {
    private final Map<Integer, GameData> gameDataMap = new HashMap<>();
    private int newGameID = -1;
    private final AuthDataAccess authDataAccess;

    public MemoryGameDataAccess(AuthDataAccess authDataAccess) {
        this.authDataAccess = authDataAccess;
    }


    @Override
    public Collection<GameData.SerializedGame> listGames() {
        // turn all games from data access to a serializable game, and return them in a collection
        HashSet<GameData.SerializedGame> allGames = new HashSet<>();
        for (GameData gameData : this.gameDataMap.values()) {
            allGames.add(new GameData.SerializedGame(gameData.gameID(), gameData.whiteUsername(),
                    gameData.blackUsername(), gameData.gameName()));
        }

        return allGames;
    }

    @Override
    public int createGame(String gameName) {
        incrementNewGameID();
        gameDataMap.put(this.newGameID, new GameData(this.newGameID, "", "", gameName,
                new ChessGame(), new HashSet<>()));
        return this.newGameID;
    }

    @Override
    public GameData getGame(int gameID) {
        return this.gameDataMap.get(gameID);
    }

    @Override
    public void addPlayer(String clientColor, int gameID) {
        GameData game = this.gameDataMap.get(gameID);
        if (clientColor.equals("WHITE")) {
            this.gameDataMap.put(gameID, new GameData(gameID, clientColor, game.blackUsername(), game.gameName(),
                    game.game(), game.spectators()));
        } else if (clientColor.equals("BLACK")) {
            this.gameDataMap.put(gameID, new GameData(gameID, game.whiteUsername(), clientColor, game.gameName(),
                    game.game(), game.spectators()));
        }
    }

    @Override
    public void addSpectator(int gameID, String authToken) throws DataAccessException {
        GameData game = this.gameDataMap.get(gameID);
        AuthData userAuth = this.authDataAccess.getAuth(authToken);
        game.spectators().add(userAuth);
    }

    @Override
    public void clearGames() {
        gameDataMap.clear();
        this.newGameID = -1;
    }

    private void incrementNewGameID() {
        this.newGameID++;
    }
}
