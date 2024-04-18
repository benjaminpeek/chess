package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import clientRepl.Repl;
import com.google.gson.Gson;
import exceptions.ResponseException;
import webSocket.MessageHandler;
import webSocket.WebSocketFacade;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Arrays;
import java.util.Collection;

import static visual.EscapeSequences.*;

public class Gameplay implements UI, MessageHandler {
    private final String serverUrl;
    private final WebSocketFacade webSocketFacade;

    public Gameplay(String serverUrl) throws ResponseException {
        this.serverUrl = serverUrl;
        this.webSocketFacade = new WebSocketFacade(serverUrl, this);
        if (Repl.playerColor != null) {
            webSocketFacade.joinPlayer();
        } else {
            webSocketFacade.joinObserver();
            Repl.drawingBoard.drawWhite(null);
        }

    }

    @Override
    public void notify(String notification) {
        ServerMessage serverMessage = new Gson().fromJson(notification, ServerMessage.class);
        switch (serverMessage.getServerMessageType()) {
            case LOAD_GAME ->  {
                LoadGame loadGame = new Gson().fromJson(notification, LoadGame.class);
                Repl.drawingBoard.setGame(loadGame.getGame().game());
                System.out.println("\n");
                redraw();
            }
            case ERROR -> printError(notification);
            case NOTIFICATION -> printNotification(notification);
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
            Repl.drawingBoard.drawBlack(null);
        } else {
            Repl.drawingBoard.drawWhite(null);
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
            ChessMove move = createMove(startPosition, endPosition);
            // now check for promotion moves (not completely finished)
            if (Repl.drawingBoard.getGame().getBoard().getPiece(move.getStartPosition()).getPieceType().equals(ChessPiece.PieceType.PAWN)) {
                Collection<ChessMove> validMoves = Repl.drawingBoard.getGame().validMoves(move.getStartPosition());
                for (ChessMove valMove : validMoves) {
                    if (valMove.getPromotionPiece() != null) {
                        move = new ChessMove(move.getStartPosition(), move.getEndPosition(), move.getPromotionPiece());
                    }
                }
            }

            try {
                webSocketFacade.makeMove(move);
            } catch (ResponseException e) {
                return e.getMessage();
            }
            return "";
        }
        throw new ResponseException(400, "move was not valid");
    }

    public String resign(String... params) throws ResponseException {
        if (params.length == 0) {
            try {
                webSocketFacade.resignGame();
            } catch (ResponseException e) {
                return e.getMessage();
            }
            return "resigned the game";
        }
        throw new ResponseException(400, "resign game command was not valid");
    }

    public String highlightMoves(String... params) throws ResponseException {
        if (params.length >= 1) {
            String startPosition = params[0];
            int startRow = startPosition.charAt(1) - '0';
            int startCol = charToInt(startPosition.charAt(0));
            ChessPosition position = new ChessPosition(startRow, startCol);
            Repl.drawingBoard.highlightMoves(position);
            return "";
        }
        throw new ResponseException(400, "highlight command was not valid");
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

    private ChessMove createMove(String startPos, String endPos) {
        int startRow = startPos.charAt(1) - '0';
        int startCol = charToInt(startPos.charAt(0));
        ChessPosition startPosition = new ChessPosition(startRow, startCol);

        int endRow = endPos.charAt(1) - '0';
        int endCol = charToInt(endPos.charAt(0));
        ChessPosition endPosition = new ChessPosition(endRow, endCol);

        return new ChessMove(startPosition, endPosition, null);
    }

    private int charToInt(char x) {
        int num = 0;
        switch (x) {
            case 'a' -> num = 1;
            case 'b' -> num = 2;
            case 'c' -> num = 3;
            case 'd' -> num = 4;
            case 'e' -> num = 5;
            case 'f' -> num = 6;
            case 'g' -> num = 7;
            case 'h' -> num = 8;
        }
        return num;
    }

}
