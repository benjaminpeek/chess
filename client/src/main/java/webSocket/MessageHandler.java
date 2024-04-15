package webSocket;

import webSocketMessages.serverMessages.ServerMessage;

public interface MessageHandler {
    void notify(ServerMessage notification);
}
