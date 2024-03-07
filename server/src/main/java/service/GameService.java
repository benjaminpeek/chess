package service;

import dataAccess.memory.MemoryGameDataAccess;

public class GameService {
    MemoryGameDataAccess gameDataAccess;

    public GameService(MemoryGameDataAccess gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    // the service accesses the data, from our memory access classes
    // listGames()
    // createGame(String authToken, String gameName)
    // joinGame()
}
