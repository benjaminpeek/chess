package handlers;

import dataAccess.DataAccessException;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoginResponse;
import response.LogoutResponse;
import response.RegisterResponse;
import com.google.gson.Gson;
import service.UserService;
import spark.*;

public class UserHandler {
    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public String registerHandler(Request req, Response res) throws BadRequestException, AlreadyTakenException,
            DataAccessException {
        // take in the HTTP JSON request, turn it into a RegisterRequest object
        RegisterRequest registerRequest = new Gson().fromJson(req.body(), RegisterRequest.class);
        // use the appropriate service's method
        RegisterResponse registerResponse = this.userService.registerService(registerRequest);
        // convert the service response to json for the res body
        res.status(200);
        return new Gson().toJson(registerResponse);
    }

    public String loginHandler(Request req, Response res) throws DataAccessException, UnauthorizedException {
        LoginRequest loginRequest = new Gson().fromJson(req.body(), LoginRequest.class);
        LoginResponse loginResponse = this.userService.loginService(loginRequest);

        res.status(200);
        return new Gson().toJson(loginResponse);
    }

    public String logoutHandler(Request req, Response res) throws DataAccessException, UnauthorizedException {
        LogoutResponse logoutResponse = this.userService.logoutService(req.headers("authorization"));

        res.status(200);
        return new Gson().toJson(logoutResponse);
    }
}
