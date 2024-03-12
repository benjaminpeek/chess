package dataAccess.interfaces;

import dataAccess.DataAccessException;
import model.UserData;

public interface UserDataAccess {
    UserData getUser(String username) throws DataAccessException;
    void createUser(String username, String password, String email) throws DataAccessException;
    void clearUsers() throws DataAccessException;
}
