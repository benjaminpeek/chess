package dataAccess.memory;

import dataAccess.interfaces.AuthDataAccess;
import dataAccess.DataAccessException;
import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDataAccess implements AuthDataAccess {

    Map<String, AuthData> authDataMap = new HashMap<>();
    @Override
    public void createAuth(String username) throws DataAccessException {

    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }
}
