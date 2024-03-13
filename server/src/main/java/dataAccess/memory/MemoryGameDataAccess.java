package dataAccess.memory;

import chess.ChessGame;
import dataAccess.interfaces.GameDataAccess;
import model.GameData;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDataAccess implements GameDataAccess {
    private final Map<Integer, GameData> gameDataMap = new HashMap<>();
    private int newGameID;

    @Override
    public Collection<GameData> listGames(String authToken) {
        return null;
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
    }

    private void incrementNewGameID() {
        this.newGameID++;
    }
}
