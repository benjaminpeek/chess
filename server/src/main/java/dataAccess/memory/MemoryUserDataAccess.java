package dataAccess.memory;

import dataAccess.DataAccessException;
import dataAccess.interfaces.UserDataAccess;
import model.UserData;

public class MemoryUserDataAccess implements UserDataAccess {
    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void createUser(UserData userData) throws DataAccessException {

    }
}
