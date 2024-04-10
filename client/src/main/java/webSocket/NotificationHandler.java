package webSocket;

import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.management.Notification;

public interface NotificationHandler {
    void notify(ServerMessage notification, String message);
}
