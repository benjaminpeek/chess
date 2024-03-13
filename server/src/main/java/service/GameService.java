package service;

import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.GameDataAccess;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import request.CreateGameRequest;
import response.CreateGameResponse;

public class GameService {
    GameDataAccess gameDataAccess;
    AuthDataAccess authDataAccess;

    public GameService(GameDataAccess gameDataAccess, AuthDataAccess authDataAccess) {
        this.gameDataAccess = gameDataAccess;
        this.authDataAccess = authDataAccess;
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
    // listGamesService()
    // createGameService(String authToken, String gameName)
    // joinGameService()
}
