package dataAccess.memory;

import chess.ChessGame;
import dataAccess.interfaces.GameDataAccess;
import model.GameData;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MemoryGameDataAccess implements GameDataAccess {
    private final Map<Integer, GameData> gameDataMap = new HashMap<>();
    private int newGameID = -1;

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
                new ChessGame()));
        return this.newGameID;
    }

    @Override
    public GameData getGame(int gameID) {
        return this.gameDataMap.get(gameID);
    }

    @Override
    public void updateGame(String clientColor, int gameID) {

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
