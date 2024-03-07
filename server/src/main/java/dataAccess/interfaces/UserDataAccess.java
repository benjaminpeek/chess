package dataAccess.interfaces;

import model.UserData;

public interface UserDataAccess {
    UserData getUser(String username);
    void createUser(String username, String password, String email);
}
