package clientRepl;

import ui.PreLogin;
import ui.UI;

import java.util.Scanner;

import static visual.EscapeSequences.*;

public class Repl {
    private final UI currentUI;

    public Repl(String serverUrl) {
        this.currentUI = new PreLogin(serverUrl);
    }

    public void run() {
        System.out.println("\uD83D\uDC36 Welcome to chess. Please register or login to begin.");
        System.out.print(currentUI.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
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
