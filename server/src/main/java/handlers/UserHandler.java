package handlers;

import response.RegisterResponse;
import com.google.gson.Gson;
import spark.*;

public class UserHandler {
    public RegisterResponse registerHandler(Request req, Response res) {


        return new RegisterResponse("hi", "mo"); // temp
    }
}
