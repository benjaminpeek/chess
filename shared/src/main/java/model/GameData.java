package model;

import chess.ChessGame;

import java.util.Collection;

public record GameData (
        int gameID,
        String whiteUsername,
        String blackUsername,
        String gameName,
        ChessGame game,
        Collection<AuthData> spectators
) {
    public record SerializedGame(
            int gameID,
            String whiteUsername,
            String blackUsername,
            String gameName
    ) {}
}
