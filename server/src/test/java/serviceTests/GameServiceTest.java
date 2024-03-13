package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.GameDataAccess;
import dataAccess.memory.MemoryAuthDataAccess;
import dataAccess.memory.MemoryGameDataAccess;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.GameData;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.JoinGameRequest;
import service.GameService;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    @Test
    void listGamesServiceSuccess() throws DataAccessException, UnauthorizedException, BadRequestException {
        AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
        GameDataAccess gameDataAccess = new MemoryGameDataAccess(authDataAccess);
        String authToken = authDataAccess.createAuth("user1");

        GameService gameService = new GameService(gameDataAccess, authDataAccess);
        gameService.createGameService(new CreateGameRequest("goodGame"), authToken);

        GameData.SerializedGame game = new GameData.SerializedGame(1, null, null,
                "goodGame");
        assertTrue(gameDataAccess.listGames().contains(game));
    }

    @Test
    void listGamesServiceThrowsUnauthorized() throws DataAccessException, UnauthorizedException, BadRequestException {
        AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
        GameDataAccess gameDataAccess = new MemoryGameDataAccess(authDataAccess);
        String authToken = authDataAccess.createAuth("user1");

        GameService gameService = new GameService(gameDataAccess, authDataAccess);
        assertThrows(UnauthorizedException.class, () -> gameService.createGameService(new CreateGameRequest("goodGame"),
                "badAuth"));
        gameService.createGameService(new CreateGameRequest("goodGame"), authToken);
    }

    @Test
    void createGameServiceSuccess() throws DataAccessException, UnauthorizedException, BadRequestException {
        AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
        GameDataAccess gameDataAccess = new MemoryGameDataAccess(authDataAccess);
        String authToken = authDataAccess.createAuth("mega man");

        GameService gameService = new GameService(gameDataAccess, authDataAccess);
        assertTrue(gameDataAccess.listGames().isEmpty());
        gameService.createGameService(new CreateGameRequest("fight night"), authToken);
        assertFalse(gameDataAccess.listGames().isEmpty());
    }

    @Test
    void createGameServiceThrowsUnauthorized() throws DataAccessException {
        AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
        GameDataAccess gameDataAccess = new MemoryGameDataAccess(authDataAccess);

        GameService gameService = new GameService(gameDataAccess, authDataAccess);
        assertTrue(gameDataAccess.listGames().isEmpty());
        assertThrows(UnauthorizedException.class, () -> gameService.createGameService(new CreateGameRequest("64"),
                "bowser"));
    }

    @Test
    void joinGameServiceSuccess() throws DataAccessException, UnauthorizedException, BadRequestException, AlreadyTakenException {
        AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
        GameDataAccess gameDataAccess = new MemoryGameDataAccess(authDataAccess);
        String authToken = authDataAccess.createAuth("batman");

        GameService gameService = new GameService(gameDataAccess, authDataAccess);
        gameService.createGameService(new CreateGameRequest("gotham"), authToken);

        Collection<GameData.SerializedGame> games = gameDataAccess.listGames();
        GameData.SerializedGame game = new GameData.SerializedGame(1, null, null,
                "gotham");

        assertTrue(games.contains(game));
        gameService.joinGameService(new JoinGameRequest("WHITE", 1), authToken);

        GameData.SerializedGame updatedGame = new GameData.SerializedGame(1, "batman", null,
                "gotham");

        Collection<GameData.SerializedGame> updatedGames = gameDataAccess.listGames();
        assertTrue(updatedGames.contains(updatedGame));
    }

    @Test
    void joinGameServiceThrowsAlreadyTaken() throws DataAccessException, UnauthorizedException, BadRequestException, AlreadyTakenException {
        AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
        GameDataAccess gameDataAccess = new MemoryGameDataAccess(authDataAccess);
        String authToken = authDataAccess.createAuth("jack sparrow");

        GameService gameService = new GameService(gameDataAccess, authDataAccess);
        gameService.createGameService(new CreateGameRequest("black pearl"), authToken);

        gameService.joinGameService(new JoinGameRequest("BLACK", 1), authToken);

        assertThrows(AlreadyTakenException.class, () -> gameService.joinGameService(new JoinGameRequest("BLACK",
                1), authToken));
    }
}