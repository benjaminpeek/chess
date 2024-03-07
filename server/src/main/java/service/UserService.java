package service;

import dataAccess.DataAccessException;
import dataAccess.memory.MemoryAuthDataAccess;
import dataAccess.memory.MemoryUserDataAccess;
import request.RegisterRequest;
import response.RegisterResponse;

public class UserService {
    MemoryUserDataAccess userDataAccess;
    MemoryAuthDataAccess authDataAccess;

    public UserService(MemoryUserDataAccess userDataAccess, MemoryAuthDataAccess authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    // the service accesses the data, from our memory access classes
    public RegisterResponse register(RegisterRequest request) throws DataAccessException {
        if (this.userDataAccess.getUser(request.username()) != null) {
            throw new DataAccessException("Error: already taken");
        }
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new DataAccessException("Error: bad request");
        }

        userDataAccess.createUser(request.username(), request.password(), request.email());
        String authToken = authDataAccess.createAuth(request.username());

        return new RegisterResponse(request.username(), authToken);
    }
    // login()
    // logout()
}