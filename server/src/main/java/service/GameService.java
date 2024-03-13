package service;

import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.GameDataAccess;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.GameData;
import request.CreateGameRequest;
import response.CreateGameResponse;
import response.ListGamesResponse;

import java.util.Collection;

public class GameService {
    GameDataAccess gameDataAccess;
    AuthDataAccess authDataAccess;

    public GameService(GameDataAccess gameDataAccess, AuthDataAccess authDataAccess) {
        this.gameDataAccess = gameDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public ListGamesResponse listGamesService(String authToken) throws DataAccessException, UnauthorizedException {
        if (this.authDataAccess.getAuth(authToken) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        Collection<GameData.SerializedGame> allGames = this.gameDataAccess.listGames();
        return new ListGamesResponse(allGames);
    }

    public CreateGameResponse createGameService(CreateGameRequest createGameRequest, String authToken) throws
            DataAccessException, BadRequestException, UnauthorizedException {
        if (this.authDataAccess.getAuth(authToken) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        if (createGameRequest.gameName() == null) {
            throw new BadRequestException("Error: bad request");
        }
        int newGameID = this.gameDataAccess.createGame(createGameRequest.gameName());

        return new CreateGameResponse(newGameID);
    }

    // the service accesses the data, from our memory access classes
    // createGameService(String authToken, String gameName)
    // joinGameService()
}
