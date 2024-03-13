package dataAccess.interfaces;

import dataAccess.DataAccessException;
import model.GameData;

import java.util.Collection;

public interface GameDataAccess {
    Collection<GameData.SerializedGame> listGames() throws DataAccessException;

    int createGame(String gameName) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    void addPlayer(String clientColor, int gameID, String authToken) throws DataAccessException;
    void addSpectator(int gameID, String authToken) throws DataAccessException;
    void clearGames() throws DataAccessException;
}
