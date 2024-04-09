package clientRepl;

import ui.PreLogin;
import ui.UI;
import webSocket.NotificationHandler;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Scanner;

import static visual.EscapeSequences.*;

public class Repl implements NotificationHandler {
    public static UI currentUI;

    public Repl(String serverUrl) {
        currentUI = new PreLogin(serverUrl);
    }

    public void run() {
        System.out.println(WHITE_KING + "Welcome to Chess. Please register or login to begin.");
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

    @Override
    public void notify(ServerMessage notification) {
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
