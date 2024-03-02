package dataAccess;

import model.UserData;

public interface UserDataAccessObject {
    UserData getUser(String username);
    void createUser(String username, String password, String email);
}
