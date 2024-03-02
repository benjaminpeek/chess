package dataAccess;

import model.AuthData;
import model.GameData;

import java.util.Collection;

public interface GameDataAccessObject {
    Collection<GameData> listGames(AuthData authData) throws DataAccessException;

    void createGame(AuthData authData, GameData gameID) throws DataAccessException;
    GameData getGame(GameData gameID) throws DataAccessException;
    void updateGame(String clientColor, GameData gameID) throws DataAccessException;
}
