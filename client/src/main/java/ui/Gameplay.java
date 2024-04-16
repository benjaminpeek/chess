package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import clientRepl.Repl;
import com.google.gson.Gson;
import exceptions.ResponseException;
import webSocket.MessageHandler;
import webSocket.WebSocketFacade;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Arrays;

import static visual.EscapeSequences.*;

public class Gameplay implements UI, MessageHandler {
    private final String serverUrl;
    private final WebSocketFacade webSocketFacade;
    private ChessGame currGame;
    private ChessBoard currBoard;
    private ChessMove move;

    public Gameplay(String serverUrl) throws ResponseException {
        this.serverUrl = serverUrl;
        this.webSocketFacade = new WebSocketFacade(serverUrl, this);
        if (Repl.playerColor != null) {
            webSocketFacade.joinPlayer();
        } else {
            webSocketFacade.joinObserver();
            Repl.drawingBoard.drawWhite();
        }

    }

    @Override
    public void notify(ServerMessage notification) {
        switch (notification.getServerMessageType()) {
            case LOAD_GAME ->  {
                System.out.println("\n");
                redraw();
            }
            case ERROR -> printError(new Gson().toJson(notification));
            case NOTIFICATION -> printNotification(new Gson().toJson(notification));
        }
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> redraw();
                case "leave" -> leave();
                case "makemove" -> move(params);
                case "resign" -> resign();
                case "highlight" -> highlightMoves(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String redraw() {
        if (Repl.playerColor != null && Repl.playerColor.equals(ChessGame.TeamColor.BLACK)) {
            Repl.drawingBoard.drawBlack();
        } else {
            Repl.drawingBoard.drawWhite();
        }
        return "";
    }

    public String leave(String... params) throws ResponseException {
        if (params.length == 0) {
            try {
                webSocketFacade.leaveGame();
                Repl.currentUI = new PostLogin(serverUrl);
            } catch (ResponseException e) {
                return e.getMessage();
            }
            return "you left the game";
        }
        throw new ResponseException(400, "leave game was not valid");
    }

    public String move(String... params) throws ResponseException {
        if (params.length >= 2) {
            String startPosition = params[0];
            String endPosition = params[1];
            // translate the letters to numbers, and split each parameter into ChessPosition objects
            try {
                webSocketFacade.makeMove(null);
                Repl.currentUI = new PostLogin(serverUrl);
            } catch (ResponseException e) {
                return e.getMessage();
            }
            return String.format("you left game " + Repl.gameID);
        }
        throw new ResponseException(400, "leave game was not valid");
    }

    public String resign(String... params) throws ResponseException {
        if (params.length == 0) {
            try {
                webSocketFacade.resignGame();
                // dont leave thiss
//                Repl.currentUI = new PostLogin(serverUrl);
            } catch (ResponseException e) {
                return e.getMessage();
            }
            return "resigned the game";
        }
        throw new ResponseException(400, "resign game command was not valid");
    }

    public String highlightMoves(String... params) {
        return null;
    }

    @Override
    public String help() {
        return """
             - redraw - redraws the chess board
             - leave - removes the user from the game (whether playing or observing)
             - makemove - allow the user to input what move they want to make <startPosition> <endPosition> => <a-h1-8> <a-h1-8>
             - resign - the user forfeits the game and the game is over
             - highlight - input the position of the piece for which you want to highlight its legal moves <piecePosition> => <a-h1-8>
             - quit - exit the program
             - help - explains what each command does
             """;
    }

    private void printNotification(String message) {
        Notification notification = new Gson().fromJson(message, Notification.class);
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_GREEN);
        System.out.println(notification.getMessage());
    }

    private void printError(String message) {
        Error error = new Gson().fromJson(message, Error.class);
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_RED);
        System.out.println(error.getMessage());
    }
}
