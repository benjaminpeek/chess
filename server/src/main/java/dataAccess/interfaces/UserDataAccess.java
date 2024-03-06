package dataAccess.interfaces;

import dataAccess.DataAccessException;
import model.UserData;

public interface UserDataAccess {
    UserData getUser(String username) throws DataAccessException;
    void createUser(UserData userData) throws DataAccessException;
}
