package ui;

import exceptions.ResponseException;
import webSocket.MessageHandler;
import webSocket.WebSocketFacade;
import webSocketMessages.serverMessages.ServerMessage;

import static visual.EscapeSequences.RESET_TEXT_COLOR;
import static visual.EscapeSequences.SET_TEXT_COLOR_GREEN;

public class Gameplay implements UI, MessageHandler {
    private final String serverUrl;
    private final WebSocketFacade webSocketFacade;

    public Gameplay(String serverUrl) throws ResponseException {
        this.serverUrl = serverUrl;
        this.webSocketFacade = new WebSocketFacade(serverUrl, this);
    }


    public String eval(String input) {

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
