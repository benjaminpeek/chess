package service;

import dataAccess.DataAccessException;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.UserDataAccess;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoginResponse;
import response.LogoutResponse;
import response.RegisterResponse;


public class UserService {
    UserDataAccess userDataAccess;
    AuthDataAccess authDataAccess;

    public UserService(UserDataAccess userDataAccess, AuthDataAccess authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public RegisterResponse registerService(RegisterRequest request) throws AlreadyTakenException, BadRequestException,
            DataAccessException {
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new BadRequestException("Error: bad request");
        }
        if (this.userDataAccess.getUser(request.username()) != null) {
            throw new AlreadyTakenException("Error: already taken");
        }

        userDataAccess.createUser(request.username(), request.password(), request.email());
        String authToken = authDataAccess.createAuth(request.username());

        return new RegisterResponse(request.username(), authToken);
    }

    public LoginResponse loginService(LoginRequest request) throws UnauthorizedException, DataAccessException {
        if (this.userDataAccess.getUser(request.username()) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        if (!request.password().equals(this.userDataAccess.getUser(request.username()).password())) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        String authToken = authDataAccess.createAuth(request.username());
        return new LoginResponse(request.username(), authToken);
    }

    public LogoutResponse logoutService(String authToken) throws DataAccessException, UnauthorizedException {
        if (this.authDataAccess.getAuth(authToken) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        this.authDataAccess.deleteAuth(authToken);
        return new LogoutResponse("logout successful");
    }
}