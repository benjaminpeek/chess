package ui;

import exceptions.ResponseException;
import request.CreateGameRequest;
import request.JoinGameRequest;
import serverFacade.ServerFacade;

import java.util.Arrays;

public class PostLogin implements UI {
    private final ServerFacade serverFacade;
    public PostLogin(String serverUrl) {
        this.serverFacade = new ServerFacade(serverUrl);
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "creategame" -> createGame(params);
                case "joingame" -> joinGame(params);
//                case "listgames" -> listGames(params);
//                case "logout" -> logout();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String createGame(String... params) throws ResponseException {
        if (params.length >= 1) {
            String gameName = params[0];
            serverFacade.createGame(new CreateGameRequest(params[0]));
            return String.format("Created new game: %s.", gameName);
        }
        throw new ResponseException(400, "Expected: <gameName>");
    }

    public String joinGame(String... params) throws ResponseException {
        if (params.length >= 1) {
            String gameID = params[1];
            String playerColor = params[0];
            serverFacade.joinGame(new JoinGameRequest(playerColor, Integer.parseInt(gameID)));
            return String.format("Joined game %s as %s", gameID, playerColor);
        }
        throw new ResponseException(400, "Expected: <gameName>");
    }

    @Override
    public String help() {
        return """
             - creategame - create a new chess game with <gameName>
             - joingame - join an existing chess game <gameID>
             - listgames - list all the current chess games
             - logout - logout of your current session
             - quit - exit the program
             - help - explains what each command does
             """;
    }
}
