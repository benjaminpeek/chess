package dataAccess.memory;

import dataAccess.interfaces.AuthDataAccess;
import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDataAccess implements AuthDataAccess {
    Map<String, AuthData> authDataMap = new HashMap<>();

    @Override
    public void createAuth(String username) {
        String newAuthToken = UUID.randomUUID().toString();
        authDataMap.put(newAuthToken, new AuthData(newAuthToken, username));
    }

    @Override
    public void deleteAuth(String authToken) {
        authDataMap.remove(authToken);
    }
}
