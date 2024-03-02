package dataAccess.memoryDataAccess;

import dataAccess.DataAccessException;
import dataAccess.GameDataAccess;
import model.GameData;

import java.util.Collection;

public class MemoryGameDataAccess implements GameDataAccess {
    @Override
    public Collection<GameData> listGames(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void createGame(String authToken, int gameID) throws DataAccessException {

    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public void updateGame(String clientColor, int gameID) throws DataAccessException {

    }
}
