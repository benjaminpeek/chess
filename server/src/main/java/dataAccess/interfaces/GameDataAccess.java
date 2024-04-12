package dataAccess.interfaces;

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
    void updateGame(int gameID, String whiteAuth, String blackAuth, ChessMove move) throws DataAccessException, InvalidMoveException;
}
