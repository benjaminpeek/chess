package dataAccess.interfaces;

import dataAccess.DataAccessException;

public interface AuthDataAccess {
    void createAuth(String username) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
}
