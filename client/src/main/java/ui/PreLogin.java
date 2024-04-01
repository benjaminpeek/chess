package ui;

import exceptions.ResponseException;
import request.LoginRequest;
import request.RegisterRequest;
import serverFacade.ServerFacade;

import java.util.Arrays;

public class PreLogin implements UI {
    private String username;
    private final ServerFacade serverFacade;
    private final String serverUrl;
    private State state = State.LOGGED_OUT;

    public PreLogin(String serverUrl) {
        this.serverUrl = serverUrl;
        this.serverFacade = new ServerFacade(serverUrl);
    }


    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> login(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws ResponseException {
        if (params.length >= 2) {
            username = String.join("-", params);
            serverFacade.register(new RegisterRequest(params[0], params[1], params[2]));
            return String.format("You registered as %s.", username);
        }
        throw new ResponseException(400, "Expected: <username> <password> <email>");
    }

    public String login(String... params) throws ResponseException {
        if (params.length >= 1) {
            state = State.LOGGED_IN;
            username = String.join("-", params);
            serverFacade.login(new LoginRequest(params[0], params[1]));
            return String.format("You signed in as %s.", username);
        }
        throw new ResponseException(400, "Expected: <username> <password>");
    }

    @Override
    public String help() {
        return null;
    }
}
