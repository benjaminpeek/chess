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
    void createAuthFails() {
        assertThrows(DataAccessException.class, () -> authDAO.createAuth(null));
    }

    @Test
    void deleteAuthSuccess() throws DataAccessException {
        String authToken = authDAO.createAuth("ben");
        assertEquals(authToken, authDAO.getAuth(authToken).authToken());
        authDAO.deleteAuth(authToken);
        assertNull(authDAO.getAuth(authToken));
    }

    @Test
    void getAuthSuccess() throws DataAccessException {
        String authToken = authDAO.createAuth("ben");
        assertEquals(authToken, authDAO.getAuth(authToken).authToken());
    }

    @Test
    void getAuthBadAuth() throws DataAccessException {
        assertNull(authDAO.getAuth("non-existent"));
    }

    @Test
    void clearAuthsTest() throws DataAccessException {
        String authToken = authDAO.createAuth("ben");
        assertEquals(authToken, authDAO.getAuth(authToken).authToken());
        authDAO.clearAuths();
        assertNull(authDAO.getAuth(authToken));
    }
}
