package handlers;

import com.google.gson.JsonSyntaxException;
import dataAccess.DataAccessException;
import dataAccess.memory.MemoryAuthDataAccess;
import dataAccess.memory.MemoryUserDataAccess;
import request.RegisterRequest;
import response.RegisterResponse;
import com.google.gson.Gson;
import service.UserService;
import spark.*;

public class UserHandler {
    private final MemoryUserDataAccess userDataAccess;
    private final MemoryAuthDataAccess authDataAccess;

    public UserHandler(MemoryUserDataAccess userDataAccess, MemoryAuthDataAccess authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public String registerHandler(Request req, Response res) {
        try {
            // build the service
            UserService userService = new UserService(this.userDataAccess, this.authDataAccess);
            // take in the HTTP JSON request, turn it into a RegisterRequest object
            RegisterRequest registerRequest = new Gson().fromJson(req.body(), RegisterRequest.class);
            // use the appropriate service's method
            RegisterResponse registerResponse = userService.registerService(registerRequest);
            // convert the service response to json for the res body
            res.status(200);
            return new Gson().toJson(registerResponse);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

//return new Gson().toJson(Map.of("message", "Error: <put your error message here")
