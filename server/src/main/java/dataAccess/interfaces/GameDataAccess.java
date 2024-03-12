package dataAccess.interfaces;

import dataAccess.DataAccessException;
import model.GameData;

import java.util.Collection;

public interface GameDataAccess {
    Collection<GameData> listGames(String authToken) throws DataAccessException;

    void createGame(String authToken, int gameID) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    void updateGame(String clientColor, int gameID) throws DataAccessException;
    void clearGames() throws DataAccessException;
}
