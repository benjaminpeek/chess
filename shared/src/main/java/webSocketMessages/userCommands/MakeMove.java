package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand {
    int gameID;
    ChessMove move;

    public MakeMove(String authToken, CommandType commandType, int gameID, ChessMove move) {
        super(authToken, commandType);
        this.gameID = gameID;
        this.move = move;
    }
}
