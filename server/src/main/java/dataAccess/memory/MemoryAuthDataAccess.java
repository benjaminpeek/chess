package dataAccess.memory;

import dataAccess.interfaces.AuthDataAccess;
import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDataAccess implements AuthDataAccess {
    Map<String, AuthData> authDataMap = new HashMap<>();

    @Override
    public String createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        authDataMap.put(authToken, new AuthData(authToken, username));

        return authToken;
    }

    @Override
    public void deleteAuth(String authToken) {
        authDataMap.remove(authToken);
    }
}
