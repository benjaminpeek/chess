package service;

import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.GameDataAccess;
import dataAccess.interfaces.UserDataAccess;
import response.ClearApplicationResponse;

public class ClearService {
    UserDataAccess userDataAccess;
    AuthDataAccess authDataAccess;
    GameDataAccess gameDataAccess;

    public ClearService(UserDataAccess userDataAccess, AuthDataAccess authDataAccess, GameDataAccess gameDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
        this.gameDataAccess = gameDataAccess;
    }

    public ClearApplicationResponse clearApplicationService(String body) throws DataAccessException {
        this.userDataAccess.clearUsers();
        this.authDataAccess.clearAuths();
        this.gameDataAccess.clearGames();

        return new ClearApplicationResponse("database cleared" + body);
    }
}
