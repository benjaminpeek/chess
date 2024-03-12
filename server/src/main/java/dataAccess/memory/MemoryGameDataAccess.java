package dataAccess.memory;

import dataAccess.DataAccessException;
import dataAccess.interfaces.GameDataAccess;
import model.GameData;
import model.UserData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDataAccess implements GameDataAccess {
    private final Map<Integer, GameData> gameDataMap = new HashMap<>();
    @Override
    public Collection<GameData> listGames(String authToken) {
        return null;
    }

    @Override
    public void createGame(String authToken, int gameID) {

    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public void updateGame(String clientColor, int gameID) {

    }

    @Override
    public void clearGames() throws DataAccessException {
        gameDataMap.clear();
    }
}
