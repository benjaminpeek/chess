package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import request.CreateGameRequest;
import request.JoinGameRequest;
import response.CreateGameResponse;
import response.JoinGameResponse;
import response.ListGamesResponse;
import service.GameService;
import spark.*;

public class GameHandler {
    private final GameService gameService;

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public String createGameHandler(Request req, Response res) throws DataAccessException, BadRequestException,
            UnauthorizedException {
        CreateGameRequest createGameRequest = new Gson().fromJson(req.body(), CreateGameRequest.class);
        CreateGameResponse createGameResponse = this.gameService.createGameService(createGameRequest,
                req.headers("authorization"));

        res.status(200);
        return new Gson().toJson(createGameResponse);
    }

    public String listGamesHandler(Request req, Response res) throws DataAccessException, UnauthorizedException {
        ListGamesResponse listGamesResponse = this.gameService.listGamesService(req.headers("authorization"));

        res.status(200);
        return new Gson().toJson(listGamesResponse);
    }

    public String joinGameHandler(Request req, Response res) throws DataAccessException, BadRequestException,
            UnauthorizedException {
        JoinGameRequest joinGameRequest = new Gson().fromJson(req.body(), JoinGameRequest.class);
        JoinGameResponse joinGameResponse = this.gameService.joinGameService(joinGameRequest,
                req.headers("authorization"));

        res.status(200);
        return new Gson().toJson(joinGameResponse);
    }
}
