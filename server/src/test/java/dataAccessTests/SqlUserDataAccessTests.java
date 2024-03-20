package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.SqlUserDataAccess;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SqlUserDataAccessTests {
    SqlUserDataAccess userDAO = new SqlUserDataAccess();

    @BeforeEach
    void setup() throws DataAccessException {
        userDAO.clearUsers();
    }

    @Test
    void getUserSuccess() throws DataAccessException {
        userDAO.createUser("ben", "pass", "ben@gmail");
        UserData expectedUser = new UserData("ben", "pass", "ben@gmail");

        assertEquals(expectedUser, userDAO.getUser("ben"));
    }

    @Test
    void getUserFails() throws DataAccessException {
        assertNull(userDAO.getUser("non-existent2002"));
    }

    @Test
    void createUserSuccess() throws DataAccessException {
        assertNull(userDAO.getUser("ben"));
        userDAO.createUser("ben", "pass", "ben@gmail");
        UserData expectedUser = new UserData("ben", "pass", "ben@gmail");
        assertEquals(expectedUser, userDAO.getUser("ben"));
    }

    @Test
    void createUserFails() throws DataAccessException {
        userDAO.createUser("ben", "pass", "ben@gmail");
        assertThrows(DataAccessException.class, () -> userDAO.createUser("ben", "pass", "ben@gmail"));
    }

    @Test
    void clearUsersTest() throws DataAccessException {
        assertNull(userDAO.getUser("ben"));
        userDAO.createUser("ben", "pass", "ben@gmail");
        UserData expectedUser = new UserData("ben", "pass", "ben@gmail");
        assertEquals(expectedUser, userDAO.getUser("ben"));
        assertNotNull(userDAO.getUser("ben"));

        userDAO.clearUsers();
        assertNull(userDAO.getUser("ben"));
    }
}
