package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.UserDataAccess;
import dataAccess.memory.MemoryAuthDataAccess;
import dataAccess.memory.MemoryUserDataAccess;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import handlers.UserHandler;
import service.UserService;
import spark.*;

import java.util.Map;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // prepare the necessary data access objects
        UserDataAccess userDataAccess = new MemoryUserDataAccess();
        AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
        // prepare the services
        UserService userService = new UserService(userDataAccess, authDataAccess);
        // prepare the handlers
        UserHandler userHandler = new UserHandler(userService);

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", userHandler::registerHandler);
        Spark.post("/session", userHandler::loginHandler);

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
