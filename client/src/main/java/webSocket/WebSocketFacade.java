package webSocket;
import chess.ChessMove;
import clientRepl.Repl;
import com.google.gson.Gson;
import exceptions.ResponseException;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {
    Session session;
    MessageHandler messageHandler;

    public WebSocketFacade(String url, MessageHandler messageHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.messageHandler = messageHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            //noinspection Convert2Lambda
            this.session.addMessageHandler(new javax.websocket.MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    messageHandler.notify(message);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void joinPlayer() throws ResponseException {
        try {
            sendMessage(new JoinPlayer(Repl.authToken, Repl.gameID, Repl.playerColor));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void joinObserver() throws ResponseException {
        try {
            sendMessage(new JoinObserver(Repl.authToken, Repl.gameID));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void makeMove(ChessMove move) throws ResponseException {
        try {
            sendMessage(new MakeMove(Repl.authToken, Repl.gameID, move));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void leaveGame() throws ResponseException {
        try {
            sendMessage(new LeaveGame(Repl.authToken, Repl.gameID));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void resignGame() throws ResponseException {
        try {
            sendMessage(new ResignGame(Repl.authToken, Repl.gameID));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void sendMessage(UserGameCommand command) throws IOException {
        this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {}

}
