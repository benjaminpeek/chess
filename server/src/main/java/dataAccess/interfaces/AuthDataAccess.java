package dataAccess.interfaces;

import dataAccess.DataAccessException;

public interface AuthDataAccess {
    String createAuth(String username) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    String getAuth(String authToken) throws DataAccessException;
    void clearAuths() throws DataAccessException;
}
