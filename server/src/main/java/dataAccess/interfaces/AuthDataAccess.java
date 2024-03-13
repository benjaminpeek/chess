package dataAccess.interfaces;

import dataAccess.DataAccessException;
import model.AuthData;

public interface AuthDataAccess {
    String createAuth(String username) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    void clearAuths() throws DataAccessException;
}
