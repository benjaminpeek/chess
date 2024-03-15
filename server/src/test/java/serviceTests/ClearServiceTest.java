package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.GameDataAccess;
import dataAccess.interfaces.UserDataAccess;
import dataAccess.memory.MemoryAuthDataAccess;
import dataAccess.memory.MemoryGameDataAccess;
import dataAccess.memory.MemoryUserDataAccess;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.RegisterRequest;
import service.ClearService;
import service.GameService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
    UserDataAccess userDataAccess = new MemoryUserDataAccess();
    GameDataAccess gameDataAccess = new MemoryGameDataAccess(authDataAccess);
    ClearService clearService = new ClearService(userDataAccess, authDataAccess, gameDataAccess);
    GameService gameService = new GameService(gameDataAccess, authDataAccess);
    UserService userService = new UserService(userDataAccess, authDataAccess);

    @Test
    void clearApplicationService() throws DataAccessException, UnauthorizedException, BadRequestException, AlreadyTakenException {
        String registerAuthToken = userService.registerService(new RegisterRequest("ben", "pass",
                "email")).authToken();
        gameService.createGameService(new CreateGameRequest("game1"), registerAuthToken);

        assertNotNull(authDataAccess.getAuth(registerAuthToken));
        assertNotNull(userDataAccess.getUser("ben"));
        assertNotNull(gameDataAccess.getGame(1));

        clearService.clearApplicationService("");

        assertNull(authDataAccess.getAuth(registerAuthToken));
        assertNull(userDataAccess.getUser("ben"));
        assertNull(gameDataAccess.getGame(1));
    }
}