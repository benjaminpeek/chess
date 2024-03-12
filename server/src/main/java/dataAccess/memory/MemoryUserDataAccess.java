package dataAccess.memory;

import dataAccess.interfaces.UserDataAccess;
import model.UserData;
import java.util.HashMap;
import java.util.Map;

public class MemoryUserDataAccess implements UserDataAccess {
    private final Map<String, UserData> userDataMap = new HashMap<>();

    @Override
    public UserData getUser(String username) {
        return userDataMap.get(username);
    }

    @Override
    public void createUser(String username, String password, String email) {
        this.userDataMap.put(username, new UserData(username, password, email));
    }

    @Override
    public void clearUsers() {
        userDataMap.clear();
    }
}
