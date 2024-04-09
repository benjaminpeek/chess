package ui;

import clientRepl.Repl;
import exceptions.ResponseException;
import serverFacade.ServerFacade;
import webSocket.NotificationHandler;
import webSocket.WebSocketFacade;

import java.util.Arrays;

public class Gameplay implements UI {
    private final String serverUrl;
    private final WebSocketFacade webSocketFacade;

    public Gameplay(String serverUrl) throws ResponseException {
        this.serverUrl = serverUrl;
        this.webSocketFacade = new WebSocketFacade(serverUrl, Repl.notificationHandler);
    }


    public String eval(String input) {
//        try {
//            var tokens = input.toLowerCase().split(" ");
//            var cmd = (tokens.length > 0) ? tokens[0] : "help";
//            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
//            return switch (cmd) {
//                case "redraw" -> redrawBoard(params);
//                case "leave" -> leaveGame(params);
//                case "makeMove" -> observeGame(params);
//                case "resign" -> listGames(params);
//                case "highlight" -> logout();
//                case "quit" -> "quit";
//                default -> help();
//            };
//        } catch (ResponseException ex) {
//            return ex.getMessage();
//        }

        return null;
    }

    @Override
    public String help() {
        return null;
    }
}
