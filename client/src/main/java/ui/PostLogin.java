package ui;

import chess.ChessGame;
import clientRepl.Repl;
import exceptions.ResponseException;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import serverFacade.ServerFacade;
import visual.DrawBoard;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static visual.EscapeSequences.*;

public class PostLogin implements UI {
    private final String serverUrl;
    private final ServerFacade serverFacade;
    private Collection<GameData> serverGames;

    public PostLogin(String serverUrl) throws ResponseException {
        this.serverFacade = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.serverGames = this.serverFacade.listGames().games();
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> createGame(params);
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
                case "list" -> listGames(params);
                case "logout" -> logout();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String createGame(String... params) throws ResponseException {
        if (params.length >= 1) {
            StringBuilder gameName = new StringBuilder(params[0]);
            for (int i = 1; i < params.length; i++) {
                gameName.append(" ");
                gameName.append(params[i]);
            }
            serverFacade.createGame(new CreateGameRequest(params[0]));
            this.serverGames = serverFacade.listGames().games();
            return String.format("Created new game: %s", gameName);
        }
        throw new ResponseException(400, "Expected: <game name>");
    }

    public String joinGame(String... params) throws ResponseException {
        if (params.length >= 1) {
            String gameID = params[1];
            String playerColor = params[0];
            try {
                serverFacade.joinGame(new JoinGameRequest(playerColor.toUpperCase(), Integer.parseInt(gameID)));
                Repl.gameID = Integer.parseInt(gameID);
                Repl.playerColor = ChessGame.TeamColor.valueOf(playerColor);
                Repl.currentUI = new Gameplay(serverUrl);
                for (GameData game : serverGames) {
                    if (game.gameID() == Integer.parseInt(gameID)) {
                        Repl.drawingBoard = new DrawBoard(game.game());
                    }
                }
            } catch (ResponseException e) {
                return e.getMessage();
            }
            Repl.drawingBoard.drawWhite();
            Repl.drawingBoard.drawBlack();
            return String.format("Joined game %s as %s", gameID, playerColor);
        }
        throw new ResponseException(400, "Expected: <playerColor: WHITE or BLACK> <game ID>");
    }

    public String observeGame(String... params) throws ResponseException {
        if (params.length >= 1) {
            String gameID = params[0];
            try {
                serverFacade.joinGame(new JoinGameRequest(null, Integer.parseInt(gameID)));
                for (GameData game : serverGames) {
                    if (game.gameID() == Integer.parseInt(gameID)) {
                        Repl.drawingBoard = new DrawBoard(game.game());
                    }
                }
                Repl.currentUI = new Gameplay(serverUrl);
            } catch (ResponseException e) {
                return e.getMessage();
            }
            Repl.drawingBoard.drawWhite();
            Repl.drawingBoard.drawBlack();
            return String.format("Joined game %s as an observer", gameID);
        }
        throw new ResponseException(400, "Expected: <game ID>");
    }

    public String listGames(String... params) throws ResponseException {
        if (params.length == 0) {
            StringBuilder gamesString = new StringBuilder();
            this.serverGames = serverFacade.listGames().games();
            Iterator<GameData> itr = serverGames.iterator();
            for (int i = 1; i <= serverGames.size(); i++) {
                GameData gameData = itr.next();

                gamesString.append(SET_TEXT_BOLD);
                gamesString.append(SET_TEXT_UNDERLINE);
                gamesString.append(SET_TEXT_COLOR_GREEN);
                gamesString.append("GAME ").append(i).append(":").append("\n");
                gamesString.append(SET_TEXT_COLOR_BLUE);
                gamesString.append(RESET_TEXT_BOLD_FAINT);
                gamesString.append(RESET_TEXT_UNDERLINE);

                gamesString.append("Game ID: ").append(gameData.gameID());
                gamesString.append("\n");
                gamesString.append("White username: ").append(gameData.whiteUsername());
                gamesString.append("\n");
                gamesString.append("Black username: ").append(gameData.blackUsername());
                gamesString.append("\n");
                gamesString.append("Game name: ").append(gameData.gameName());
                gamesString.append("\n");
                gamesString.append("\n");
            }
            return gamesString.toString();
        }
        throw new ResponseException(400, "Failed to list games");
    }

    public String logout(String... params) throws ResponseException {
        if (params.length == 0) {
            try {
                serverFacade.logout();
                Repl.currentUI = new PreLogin(serverUrl);
                System.out.print(RESET_TEXT_COLOR);
            } catch (ResponseException e) {
                return e.getMessage();
            }
            System.out.print(Repl.currentUI.help());
            return "Successfully logged out";
        }
        throw new ResponseException(400, "Failed to logout user");
    }

    @Override
    public String help() {
        return """
             - create - create a new chess game with <gameName>
             - join - join an existing chess game <playerColor: WHITE or BLACK> <gameID>
             - observe - join a game as an observer <gameID>
             - list - list all the current chess games
             - logout - logout of your current session
             - quit - exit the program
             - help - explains what each command does
             """;
    }
}
