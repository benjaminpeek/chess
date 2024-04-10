package ui;

import clientRepl.Repl;
import exceptions.ResponseException;
import serverFacade.ServerFacade;
import webSocket.NotificationHandler;
import webSocket.WebSocketFacade;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Arrays;

import static visual.EscapeSequences.RESET_TEXT_COLOR;
import static visual.EscapeSequences.SET_TEXT_COLOR_GREEN;

public class Gameplay implements UI, NotificationHandler {
    private final String serverUrl;
    private final WebSocketFacade webSocketFacade;

    public Gameplay(String serverUrl) throws ResponseException {
        this.serverUrl = serverUrl;
        this.webSocketFacade = new WebSocketFacade(serverUrl, this);
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

    @Override
    public void notify(ServerMessage notification, String originalMessage) {
        switch (notification.getServerMessageType()) {
            case LOAD_GAME -> {
                System.out.println();
            }
            case ERROR -> {
                System.out.println("no");
            }
            case NOTIFICATION -> {

            }
        }
        //System.out.println(SET_TEXT_COLOR_RED + notification.getServerMessageType());
        printPrompt();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_GREEN);
    }
}
