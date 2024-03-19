package dataAccess;

import dataAccess.interfaces.GameDataAccess;
import model.GameData;

import java.util.Collection;

public class SqlGameDataAccess implements GameDataAccess {
    String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
              `id` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256) NOT NULL,
              `blackUsername` varchar(256) NOT NULL,
              `gameName` varchar(256) NOT NULL,
              `game` TEXT NOT NULL,
              PRIMARY KEY (`id`)
            );
            """
    };

    @Override
    public Collection<GameData.SerializedGame> listGames() throws DataAccessException {
        return null;
    }

    @Override
    public int createGame(String gameName) throws DataAccessException {
        return 0;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public void addPlayer(String clientColor, int gameID, String authToken) throws DataAccessException {

    }

    @Override
    public void clearGames() throws DataAccessException {

    }
}
