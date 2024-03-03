package dataAccess.memoryDataAccess;

import dataAccess.DataAccessException;
import dataAccess.UserDataAccess;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

public class MemoryUserDataAccess implements UserDataAccess {
    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void createUser(UserData userData) throws DataAccessException {

    }
}
