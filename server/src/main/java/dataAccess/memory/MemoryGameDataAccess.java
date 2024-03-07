package dataAccess.memory;

import dataAccess.interfaces.GameDataAccess;
import model.GameData;

import java.util.Collection;

public class MemoryGameDataAccess implements GameDataAccess {
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
}
