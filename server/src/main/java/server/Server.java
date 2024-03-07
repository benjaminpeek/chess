package server;

import dataAccess.memory.MemoryAuthDataAccess;
import dataAccess.memory.MemoryUserDataAccess;
import handlers.UserHandler;
import service.UserService;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // prepare the necessary data access objects
        MemoryUserDataAccess userDataAccess = new MemoryUserDataAccess();
        MemoryAuthDataAccess authDataAccess = new MemoryAuthDataAccess();
        // prepare the services
        UserService userService = new UserService(userDataAccess, authDataAccess);
        // prepare the handlers
        UserHandler userHandler = new UserHandler(userService);

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", userHandler::registerHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
