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
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.JoinGameRequest;
import service.ClearService;
import service.GameService;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
    UserDataAccess userDataAccess = new MemoryUserDataAccess();
    GameDataAccess gameDataAccess = new MemoryGameDataAccess(authDataAccess);
    ClearService clearService = new ClearService(userDataAccess, authDataAccess, gameDataAccess);

    @BeforeEach
    void setup() throws DataAccessException {
        clearService.clearApplicationService("");
    }

    @Test
    void listGamesServiceSuccess() throws DataAccessException, UnauthorizedException, BadRequestException {
        String authToken = authDataAccess.createAuth("user1");

        GameService gameService = new GameService(gameDataAccess, authDataAccess);
        gameService.createGameService(new CreateGameRequest("goodGame"), authToken);

        GameData game = new GameData(1, null, null,
                "goodGame", gameDataAccess.listGames().iterator().next().game());
        assertTrue(gameDataAccess.listGames().contains(game));
    }

    @Test
    void listGamesServiceThrowsUnauthorized() throws DataAccessException, UnauthorizedException, BadRequestException {
        String authToken = authDataAccess.createAuth("user1");

        GameService gameService = new GameService(gameDataAccess, authDataAccess);
        assertThrows(UnauthorizedException.class, () -> gameService.createGameService(new CreateGameRequest("goodGame"),
                "badAuth"));
        gameService.createGameService(new CreateGameRequest("goodGame"), authToken);
    }

    @Test
    void createGameServiceSuccess() throws DataAccessException, UnauthorizedException, BadRequestException {
        String authToken = authDataAccess.createAuth("mega man");

        GameService gameService = new GameService(gameDataAccess, authDataAccess);
        assertTrue(gameDataAccess.listGames().isEmpty());
        gameService.createGameService(new CreateGameRequest("fight night"), authToken);
        assertFalse(gameDataAccess.listGames().isEmpty());
    }

    @Test
    void createGameServiceThrowsUnauthorized() throws DataAccessException {
        GameService gameService = new GameService(gameDataAccess, authDataAccess);
        assertTrue(gameDataAccess.listGames().isEmpty());
        assertThrows(UnauthorizedException.class, () -> gameService.createGameService(new CreateGameRequest("64"),
                "bowser"));
    }

    @Test
    void joinGameServiceSuccess() throws DataAccessException, UnauthorizedException, BadRequestException, AlreadyTakenException {
        String authToken = authDataAccess.createAuth("batman");

        GameService gameService = new GameService(gameDataAccess, authDataAccess);
        gameService.createGameService(new CreateGameRequest("gotham"), authToken);

        Collection<GameData> games = gameDataAccess.listGames();
        GameData game = new GameData(1, null, null,
                "gotham", games.iterator().next().game());

        assertTrue(games.contains(game));
        gameService.joinGameService(new JoinGameRequest("WHITE", 1), authToken);

        GameData updatedGame = new GameData(1, "batman", null,
                "gotham", games.iterator().next().game());

        Collection<GameData> updatedGames = gameDataAccess.listGames();
        assertTrue(updatedGames.contains(updatedGame));
    }

    @Test
    void joinGameServiceThrowsAlreadyTaken() throws DataAccessException, UnauthorizedException, BadRequestException, AlreadyTakenException {
        String authToken = authDataAccess.createAuth("jack sparrow");

        GameService gameService = new GameService(gameDataAccess, authDataAccess);
        gameService.createGameService(new CreateGameRequest("black pearl"), authToken);

        gameService.joinGameService(new JoinGameRequest("BLACK", 1), authToken);

        assertThrows(AlreadyTakenException.class, () -> gameService.joinGameService(new JoinGameRequest("BLACK",
                1), authToken));
    }
}