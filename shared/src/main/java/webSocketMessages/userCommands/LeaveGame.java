package webSocketMessages.userCommands;

public class LeaveGame extends UserGameCommand {
    int gameID;

    public LeaveGame(String authToken, int gameID) {
        super(authToken, CommandType.LEAVE);
        this.gameID = gameID;
    }


    public int gameID() {
        return this.gameID;
    }

}
