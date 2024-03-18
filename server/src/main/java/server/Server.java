package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.SqlUserDataAccess;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.GameDataAccess;
import dataAccess.interfaces.UserDataAccess;
import dataAccess.memory.MemoryAuthDataAccess;
import dataAccess.memory.MemoryGameDataAccess;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import handlers.ClearHandler;
import handlers.GameHandler;
import handlers.UserHandler;
import service.ClearService;
import service.GameService;
import service.UserService;
import spark.*;

import java.util.Map;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // prepare the data access points
        UserDataAccess userDataAccess = new SqlUserDataAccess();
        AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
        GameDataAccess gameDataAccess = new MemoryGameDataAccess(authDataAccess);


        // prepare the services
        ClearService clearService = new ClearService(userDataAccess, authDataAccess, gameDataAccess);
        UserService userService = new UserService(userDataAccess, authDataAccess);
        GameService gameService = new GameService(gameDataAccess, authDataAccess);

        // prepare the handlers
        ClearHandler clearHandler = new ClearHandler(clearService);
        UserHandler userHandler = new UserHandler(userService);
        GameHandler gameHandler = new GameHandler(gameService);

        // Register endpoints and handle exceptions here.
        // endpoints
        Spark.delete("/db", clearHandler::clearApplicationHandler);
        Spark.post("/user", userHandler::registerHandler);
        Spark.post("/session", userHandler::loginHandler);
        Spark.delete("/session", userHandler::logoutHandler);
        Spark.get("/game", gameHandler::listGamesHandler);
        Spark.post("/game", gameHandler::createGameHandler);
        Spark.put("/game", gameHandler::joinGameHandler);

        // exceptions
        Spark.exception(DataAccessException.class, (e, request, response) -> {
            response.status(500);
            response.body(new Gson().toJson(Map.of("message", e.getMessage())));
        });
        Spark.exception(AlreadyTakenException.class, (e, request, response) -> {
            response.status(403);
            response.body(new Gson().toJson(Map.of("message", e.getMessage())));
        });
        Spark.exception(BadRequestException.class, (e, request, response) -> {
            response.status(400);
            response.body(new Gson().toJson(Map.of("message", e.getMessage())));
        });
        Spark.exception(UnauthorizedException.class, (e, request, response) -> {
            response.status(401);
            response.body(new Gson().toJson(Map.of("message", e.getMessage())));
        });

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
