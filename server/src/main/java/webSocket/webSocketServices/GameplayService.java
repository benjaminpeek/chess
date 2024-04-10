package webSocket.webSocketServices;

import service.ClearService;
import service.GameService;
import service.UserService;

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


    public void joinPlayer() {
        System.out.println("joined as player");
    }

    public void joinObserver() {
        System.out.println("joined as observer");
    }

    public void makeMove() {
        System.out.println("move made");
    }

    public void leaveGame() {
        System.out.println("left game");
    }

    public void resignGame() {
        System.out.println("resigned game");
    }
}
