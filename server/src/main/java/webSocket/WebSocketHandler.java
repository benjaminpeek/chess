package webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocket.webSocketServices.GameplayService;
import webSocketMessages.userCommands.*;

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
            case JOIN_PLAYER -> gameplayService.joinPlayer((JoinPlayer) userGameCommand);
            case JOIN_OBSERVER -> gameplayService.joinObserver((JoinObserver) userGameCommand);
            case MAKE_MOVE -> gameplayService.makeMove((MakeMove) userGameCommand);
            case LEAVE -> gameplayService.leaveGame((LeaveGame) userGameCommand);
            case RESIGN -> gameplayService.resignGame((ResignGame) userGameCommand);
        }
    }

}
