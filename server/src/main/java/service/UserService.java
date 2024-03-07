package service;

import dataAccess.DataAccessException;
import dataAccess.memory.MemoryAuthDataAccess;
import dataAccess.memory.MemoryUserDataAccess;
import request.RegisterRequest;

public class UserService {
    MemoryUserDataAccess userDataAccess;
    MemoryAuthDataAccess authDataAccess;

    public UserService(MemoryUserDataAccess userDataAccess, MemoryAuthDataAccess authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    // the service accesses the data, from our memory access classes
    public void register(RegisterRequest request) throws DataAccessException {
        if (this.userDataAccess.getUser(request.username()) != null) {
            throw new DataAccessException("Error: already taken");
        }
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new DataAccessException("Error: bad request");
        }

        userDataAccess.createUser(request.username(), request.password(), request.email());
        authDataAccess.createAuth(request.username());
    }
    // login()
    // logout()
}