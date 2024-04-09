package ui;

import clientRepl.Repl;
import exceptions.ResponseException;
import serverFacade.ServerFacade;
import webSocket.NotificationHandler;
import webSocket.WebSocketFacade;

public class Gameplay implements UI {
    private final String serverUrl;
    private final WebSocketFacade webSocketFacade;

    public Gameplay(String serverUrl) throws ResponseException {
        this.serverUrl = serverUrl;
        this.webSocketFacade = new WebSocketFacade(serverUrl, Repl.notificationHandler);
    }

    @Override
    public String eval(String input) {
        return null;
    }

    @Override
    public String help() {
        return null;
    }
}
