package dataAccess;

import model.AuthData;
import model.UserData;

public interface AuthDataAccessObject {
    void createAuth(UserData username) throws DataAccessException;
    void deleteAuth(AuthData authData) throws DataAccessException;
}
