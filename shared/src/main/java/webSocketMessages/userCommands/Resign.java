package webSocketMessages.userCommands;

public class Resign extends UserGameCommand {
    int gameID;

    public Resign(String authToken, CommandType commandType, int gameID) {
        super(authToken, commandType);
        this.gameID = gameID;
    }
}
