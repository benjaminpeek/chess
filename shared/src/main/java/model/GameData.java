package model;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, ChessGame game) {

    @Override
    public String toString() {
        return "GameData{" +
                "gameID=" + gameID +
                ", whiteUsername='" + whiteUsername + '\'' +
                ", blackUsername='" + blackUsername + '\'' +
                ", game=" + game +
                '}';
    }
}
