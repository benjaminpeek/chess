package dataAccess.memory;

import dataAccess.interfaces.UserDataAccess;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDataAccess implements UserDataAccess {
    Map<String, UserData> users = new HashMap<>();

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }

    @Override
    public void createUser(String username, String password, String email) {
        this.users.put(username, new UserData(username, password, email));
    }
}
