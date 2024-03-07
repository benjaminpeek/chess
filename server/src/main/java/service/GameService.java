package service;

import dataAccess.memory.MemoryGameDataAccess;

public class GameService {
    MemoryGameDataAccess gameDataAccess;

    public GameService(MemoryGameDataAccess gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    // the service accesses the data, from our memory access classes
    // listGamesService()
    // createGameService(String authToken, String gameName)
    // joinGameService()
}
