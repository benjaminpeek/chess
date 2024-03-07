package handlers;

import dataAccess.DataAccessException;
import request.RegisterRequest;
import response.RegisterResponse;
import com.google.gson.Gson;
import service.UserService;
import spark.*;

public class UserHandler {
    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public String registerHandler(Request req, Response res) throws DataAccessException {
        // take in the HTTP JSON request, turn it into a RegisterRequest object
        RegisterRequest registerRequest = new Gson().fromJson(req.body(), RegisterRequest.class);
        // use the appropriate service's method
        RegisterResponse registerResponse = userService.registerService(registerRequest);
        // convert the service response to json for the res body
        res.body(new Gson().toJson(registerResponse));
        res.status(200);

        return res.body();
    }
}
