package dataAccess.interfaces;

import dataAccess.DataAccessException;

public interface AuthDataAccess {
    String createAuth(String username) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
}
