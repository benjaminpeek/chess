package webSocketMessages.userCommands;

public class Leave extends UserGameCommand {
    int gameID;

    public Leave(String authToken, CommandType commandType, int gameID) {
        super(authToken, commandType);
        this.gameID = gameID;
    }
}
