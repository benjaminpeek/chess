package webSocketMessages.userCommands;

public class ResignGame extends UserGameCommand {
    int gameID;

    public ResignGame(String authToken, int gameID) {
        super(authToken, CommandType.RESIGN);
        this.gameID = gameID;
    }


    public int gameID() {
        return this.gameID;
    }

}
