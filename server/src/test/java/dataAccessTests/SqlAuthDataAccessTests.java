package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.SqlAuthDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SqlAuthDataAccessTests {
    SqlAuthDataAccess authDAO = new SqlAuthDataAccess();

    @BeforeEach
    void setup() throws DataAccessException {
        authDAO.clearAuths();
    }

    @Test
    void createAuthSuccess() throws DataAccessException {
        String authToken = authDAO.createAuth("ben");
        assertNotNull(authDAO.getAuth(authToken));
    }

    @Test
    void createAuthFails() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> authDAO.createAuth(null));
    }

    @Test
    void deleteAuthSuccess() throws DataAccessException {
        String authToken = authDAO.createAuth("ben");
    }
}
