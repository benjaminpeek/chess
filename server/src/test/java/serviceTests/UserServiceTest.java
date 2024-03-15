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
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoginResponse;
import service.ClearService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
    UserDataAccess userDataAccess = new MemoryUserDataAccess();
    GameDataAccess gameDataAccess = new MemoryGameDataAccess(authDataAccess);
    UserService userService = new UserService(userDataAccess, authDataAccess);
    ClearService clearService = new ClearService(userDataAccess, authDataAccess, gameDataAccess);

    @BeforeEach
    void setup() throws DataAccessException {
        clearService.clearApplicationService("");
    }

    @Test
    void registerServiceSuccess() throws DataAccessException, BadRequestException, AlreadyTakenException {
        assertNull(userDataAccess.getUser("bensleepr"));

        userService.registerService(new RegisterRequest("bensleepr", "passwizz", "ben8@gmail.com"));
        UserData expected = new UserData("bensleepr", "passwizz", "ben8@gmail.com");

        assertEquals(expected, userDataAccess.getUser("bensleepr"));
    }

    @Test
    void registerServiceThrowsAlreadyTaken() throws BadRequestException, AlreadyTakenException, DataAccessException {
        userService.registerService(new RegisterRequest("bensleepr", "passwizz", "ben8@gmail.com"));
        assertThrows(AlreadyTakenException.class, () -> userService.registerService(new RegisterRequest("bensleepr",
                "passwizz", "ben8@gmail.com")));
    }

    @Test
    void loginServiceSuccess() throws BadRequestException, AlreadyTakenException, DataAccessException, UnauthorizedException {
        userService.registerService(new RegisterRequest("bensleepr", "passwizz", "ben8@gmail.com"));
        assertNotNull(userDataAccess.getUser("bensleepr"));
        assertEquals("bensleepr", userService.loginService(new LoginRequest("bensleepr", "passwizz")).username());
    }

    @Test
    void loginServiceThrowsUnauthorized() throws BadRequestException, AlreadyTakenException, DataAccessException {
        userService.registerService(new RegisterRequest("bensleepr", "passwizz", "ben8@gmail.com"));
        assertThrows(UnauthorizedException.class, () -> userService.loginService(new LoginRequest("bensleepr",
                "wrongPass")));
    }

    @Test
    void logoutServiceSuccess() throws BadRequestException, AlreadyTakenException, DataAccessException, UnauthorizedException {
        userService.registerService(new RegisterRequest("bensleepr", "passwizz", "ben8@gmail.com"));
        LoginResponse loginResponse = userService.loginService(new LoginRequest("bensleepr", "passwizz"));
        assertNotNull(authDataAccess.getAuth(loginResponse.authToken()));

        userService.logoutService(loginResponse.authToken());
        assertNull(authDataAccess.getAuth(loginResponse.authToken()));
    }

    @Test
    void logoutServiceThrows() throws BadRequestException, AlreadyTakenException, DataAccessException, UnauthorizedException {
        userService.registerService(new RegisterRequest("bensleepr", "passwizz", "ben8@gmail.com"));
        userService.loginService(new LoginRequest("bensleepr", "passwizz"));

        assertThrows(UnauthorizedException.class, () -> userService.logoutService("wrongAuth"));
    }
}