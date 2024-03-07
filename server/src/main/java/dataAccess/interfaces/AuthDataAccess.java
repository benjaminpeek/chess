package dataAccess.interfaces;

public interface AuthDataAccess {
    String createAuth(String username);
    void deleteAuth(String authToken);

    String getAuth(String authToken);
}
