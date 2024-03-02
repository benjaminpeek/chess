package dataAccess;

public interface AuthDataAccess {
    void createAuth(String username) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
}
