package clientRepl;

import chess.ChessGame;
import exceptions.ResponseException;
import ui.Gameplay;
import ui.PostLogin;
import ui.PreLogin;
import ui.UI;
import visual.DrawBoard;

import java.util.Scanner;

import static visual.EscapeSequences.*;

public class Repl {
    public static UI currentUI;
    public static String username;
    public static String authToken;
    public static int gameID;
    public static ChessGame.TeamColor playerColor;
    public static DrawBoard drawingBoard;

    public Repl(String serverUrl) {
        currentUI = new PreLogin(serverUrl);
    }

    public void run() {
        System.out.println(WHITE_KING + "Welcome to Chess. Please register or login to begin.");
        System.out.print(currentUI.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (result != null && !result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = currentUI.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_GREEN);
    }
}
