package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import request.CreateGameRequest;
import response.CreateGameResponse;
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
}
