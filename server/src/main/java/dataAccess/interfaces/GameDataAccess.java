package dataAccess.interfaces;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import dataAccess.DataAccessException;
import model.GameData;

import java.util.Collection;

public interface GameDataAccess {
    Collection<GameData> listGames() throws DataAccessException;

    int createGame(String gameName) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    void addPlayer(String clientColor, int gameID, String authToken) throws DataAccessException;
    void removePlayer(String clientColor, int gameID, String authToken) throws DataAccessException;
    void clearGames() throws DataAccessException;
    default void updateGame(int gameID, ChessMove move) throws DataAccessException, InvalidMoveException {
        ChessGame chessGame = getGame(gameID).game();
        if (move != null) {
            chessGame.makeMove(move);
        }
    }
}
