package service;

import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.GameDataAccess;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import response.CreateGameResponse;
import response.JoinGameResponse;
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
        if (createGameRequest.gameName() == null) {
            throw new BadRequestException("Error: bad request");
        }
        if (this.authDataAccess.getAuth(authToken) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        int newGameID = this.gameDataAccess.createGame(createGameRequest.gameName());
        return new CreateGameResponse(newGameID);
    }

    public JoinGameResponse joinGameService(JoinGameRequest joinGameRequest, String authToken) throws
            DataAccessException, BadRequestException, UnauthorizedException, AlreadyTakenException {
        if (this.gameDataAccess.getGame(joinGameRequest.gameID()) == null) {
            throw new BadRequestException("Error: bad request");
        }
        if (this.authDataAccess.getAuth(authToken) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        if ("WHITE".equals(joinGameRequest.playerColor())) {
            if (this.gameDataAccess.getGame(joinGameRequest.gameID()).whiteUsername() != null) {
                throw new AlreadyTakenException("Error: already taken");
            }
        }
        if ("BLACK".equals(joinGameRequest.playerColor())) {
            if (this.gameDataAccess.getGame(joinGameRequest.gameID()).blackUsername() != null) {
                throw new AlreadyTakenException("Error: already taken");
            }
        }

        if (!joinGameRequest.playerColor().equals("WHITE") && !joinGameRequest.playerColor().equals("BLACK")) {
            throw new BadRequestException("Error: bad request");
        }

        // add caller to game as player
        this.gameDataAccess.addPlayer(joinGameRequest.playerColor(), joinGameRequest.gameID(), authToken);
        return new JoinGameResponse("joined game as " + joinGameRequest.playerColor());
    }
}
