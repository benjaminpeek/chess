package webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocket.webSocketServices.GameplayService;
import webSocketMessages.userCommands.UserGameCommand;

@WebSocket
public class WebSocketHandler {
    WebSocketSessions webSocketSessions;
    GameplayService gameplayService;

    WebSocketHandler(WebSocketSessions webSocketSessions, GameplayService gameplayService) {
        this.webSocketSessions = webSocketSessions;
        this.gameplayService = gameplayService;
    }


    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> gameplayService.joinPlayer();
            case JOIN_OBSERVER -> gameplayService.joinObserver();
            case MAKE_MOVE -> gameplayService.makeMove();
            case LEAVE -> gameplayService.leaveGame();
            case RESIGN -> gameplayService.resignGame();
        }
    }

}
