package dataAccess.interfaces;

import model.GameData;

import java.util.Collection;

public interface GameDataAccess {
    Collection<GameData> listGames(String authToken);

    void createGame(String authToken, int gameID);
    GameData getGame(int gameID);
    void updateGame(String clientColor, int gameID);
}
