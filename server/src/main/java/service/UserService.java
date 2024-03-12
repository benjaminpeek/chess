package service;

import dataAccess.memory.MemoryAuthDataAccess;
import dataAccess.memory.MemoryUserDataAccess;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoginResponse;
import response.RegisterResponse;

public class UserService {
    MemoryUserDataAccess userDataAccess;
    MemoryAuthDataAccess authDataAccess;

    public UserService(MemoryUserDataAccess userDataAccess, MemoryAuthDataAccess authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public RegisterResponse registerService(RegisterRequest request) throws AlreadyTakenException, BadRequestException {
        if (this.userDataAccess.getUser(request.username()) != null) {
            throw new AlreadyTakenException("Error: already taken");
        }
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new BadRequestException("Error: bad request");
        }

        userDataAccess.createUser(request.username(), request.password(), request.email());
        String authToken = authDataAccess.createAuth(request.username());

        return new RegisterResponse(request.username(), authToken);
    }

    public LoginResponse loginService(LoginRequest request) throws UnauthorizedException {
        if (!request.password().equals(this.userDataAccess.getUser(request.username()).password())) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        String authToken = authDataAccess.createAuth(request.username());

        return new LoginResponse(request.username(), authToken);
    }

    // logoutService()
}