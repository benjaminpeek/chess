package ui;

import clientRepl.Repl;
import exceptions.ResponseException;
import request.LoginRequest;
import request.RegisterRequest;
import serverFacade.ServerFacade;

import java.util.Arrays;

import static visual.EscapeSequences.*;

public class PreLogin implements UI {
    private final String serverUrl;
    private final ServerFacade serverFacade;

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
            Repl.username = params[0];
            serverFacade.register(new RegisterRequest(params[0], params[1], params[2]));
            Repl.authToken = serverFacade.getAuthToken();
            return String.format("You registered as %s.", Repl.username);
        }
        throw new ResponseException(400, "Expected: <username> <password> <email>");
    }

    public String login(String... params) throws ResponseException {
        if (params.length >= 1) {
            Repl.username = params[0];
            // change the "state" to logged in
            try {
                // debugg this stuff buddy
                serverFacade.login(new LoginRequest(params[0], params[1]));
                Repl.authToken = serverFacade.getAuthToken();
                Repl.currentUI = new PostLogin(serverUrl);
                System.out.print(RESET_TEXT_COLOR);
            } catch (ResponseException e) {
                return e.getMessage();
            }
            System.out.print(Repl.currentUI.help());
            return String.format("WELCOME, %s!", Repl.username);
        }
        throw new ResponseException(400, "Expected: <username> <password>");
    }

    @Override
    public String help() {
        return """
             - register - register a new user with <username> <password> <email>
             - login - log an existing user in with <username> <password>
             - quit - exit the program
             - help - explains what each command does
             """;
    }
}
