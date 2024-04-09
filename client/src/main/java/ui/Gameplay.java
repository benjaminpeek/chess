package ui;

import exceptions.ResponseException;
import serverFacade.ServerFacade;
import webSocket.NotificationHandler;
import webSocket.WebSocketFacade;

public class Gameplay implements UI {
    private String username;
    private final String serverUrl;
    private final NotificationHandler notificationHandler;
    private final WebSocketFacade webSocketFacade;

    public Gameplay(String serverUrl, NotificationHandler notificationHandler) throws ResponseException {
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
        this.webSocketFacade = new WebSocketFacade(serverUrl, notificationHandler);
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
