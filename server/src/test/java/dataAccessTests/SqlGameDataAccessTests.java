package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.SqlAuthDataAccess;
import dataAccess.SqlGameDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SqlGameDataAccessTests {
    SqlAuthDataAccess authDAO = new SqlAuthDataAccess();
    SqlGameDataAccess gameDAO = new SqlGameDataAccess(authDAO);

    @BeforeEach
    void setup() throws DataAccessException {
        gameDAO.clearGames();
    }

    @Test
    void listGamesSuccess() throws DataAccessException {
        gameDAO.createGame("awesome game");
        assertNotEquals(0, gameDAO.listGames().size());
    }

    @Test
    void listGamesNoGames() throws DataAccessException {
        assertEquals(0, gameDAO.listGames().size());
    }

    @Test
    void createGameSuccess() throws DataAccessException {
        assertEquals(1, gameDAO.createGame("awesome game"));
    }

    @Test
    void createGameThrows() {
        assertThrows(DataAccessException.class, () -> gameDAO.createGame(null));
    }

    @Test
    void getGameSuccess() throws DataAccessException {
        gameDAO.createGame("game 1");
        assertNotNull(gameDAO.getGame(1));
    }

    @Test
    void getGameFailBadID() throws DataAccessException {
        assertNull(gameDAO.getGame(100));
    }

    @Test
    void addPlayerSuccess() throws DataAccessException {
        String authToken = authDAO.createAuth("ben");
        int gameID = gameDAO.getGame(gameDAO.createGame("awesome game")).gameID();
        gameDAO.addPlayer("WHITE", gameID, authToken);
        assertNotNull(gameDAO.getGame(gameID));
    }

    @Test
    void addPlayerFail() {
        assertThrows(NullPointerException.class, () -> gameDAO.addPlayer("BLACK", 0, "falseToken"));
    }

    @Test
    void clearGamesTest() throws DataAccessException {
        int gameID = gameDAO.createGame("awesome game");
        assertEquals(1, gameDAO.getGame(gameID).gameID());

        gameDAO.clearGames();
        assertNull(gameDAO.getGame(1));
    }

}
