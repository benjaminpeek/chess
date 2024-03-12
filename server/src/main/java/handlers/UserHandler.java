package handlers;

import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.UserDataAccess;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoginResponse;
import response.RegisterResponse;
import com.google.gson.Gson;
import service.UserService;
import spark.*;

import java.util.Map;

public class UserHandler {
    private final UserDataAccess userDataAccess;
    private final AuthDataAccess authDataAccess;
    //private final UserService userService; ????????????????????????????????????

    public UserHandler(UserDataAccess userDataAccess, AuthDataAccess authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
        //this.userService = userService; ????????????????????????????????????
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
        } catch (AlreadyTakenException e) {
            res.status(403);
            return new Gson().toJson(Map.of("message", e.getMessage()));
        } catch (BadRequestException e) {
            res.status(400);
            return new Gson().toJson(Map.of("message", e.getMessage()));
        } catch (DataAccessException e) {
            res.status(500);
            return new Gson().toJson(Map.of("message", e.getMessage()));
        }
    }

    public String loginHandler(Request req, Response res) throws DataAccessException {
        try {
            UserService userService = new UserService(this.userDataAccess, this.authDataAccess);

            LoginRequest loginRequest = new Gson().fromJson(req.body(), LoginRequest.class);
            LoginResponse loginResponse = userService.loginService(loginRequest);

            res.status(200);
            return new Gson().toJson(loginResponse);
        } catch (UnauthorizedException e) {
            res.status(401);
            return new Gson().toJson(Map.of("message", e.getMessage()));
        }
    }
}

//return new Gson().toJson(Map.of("message", "Error: <put your error message here")
