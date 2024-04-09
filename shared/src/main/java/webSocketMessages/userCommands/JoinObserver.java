package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand {
    int gameID;

    public JoinObserver(String authToken, CommandType commandType, int gameID) {
        super(authToken, commandType);
        this.gameID = gameID;
    }
}
