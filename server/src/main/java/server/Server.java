package server;

import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.UserDataAccess;
import dataAccess.memory.MemoryAuthDataAccess;
import dataAccess.memory.MemoryUserDataAccess;
import handlers.UserHandler;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // prepare the necessary data access objects
        UserDataAccess userDataAccess = new MemoryUserDataAccess();
        AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
        // prepare the services ???????????????????????????????????????????????????????????????????????????????????????
        // UserService userService = new UserService(userDataAccess, authDataAccess); ????????????????????????????????
        // prepare the handlers
        UserHandler userHandler = new UserHandler(userDataAccess, authDataAccess);

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", userHandler::registerHandler);
        Spark.post("/session", userHandler::loginHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
