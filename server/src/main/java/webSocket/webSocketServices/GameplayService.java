package webSocket.webSocketServices;

import service.ClearService;
import service.GameService;
import service.UserService;
import webSocketMessages.userCommands.*;

public class GameplayService {

    String data;
    ClearService clearService;
    GameService gameService;
    UserService userService;

    GameplayService(String data, ClearService clearService, GameService gameService, UserService userService) {
        this.data = data;
        this.clearService = clearService;
        this.gameService = gameService;
        this.userService = userService;
    }


    public void joinPlayer(JoinPlayer joinPlayerCommand) {
        System.out.println("joined as player");
    }

    public void joinObserver(JoinObserver joinObserverCommand) {
        System.out.println("joined as observer");
    }

    public void makeMove(MakeMove makeMoveCommand) {
        System.out.println("move made");
    }

    public void leaveGame(LeaveGame leaveGameGameCommand) {
        System.out.println("left game");
    }

    public void resignGame(ResignGame resignGameGameCommand) {
        System.out.println("resigned game");
    }
}
