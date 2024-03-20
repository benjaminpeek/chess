package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.GameDataAccess;
import model.AuthData;
import model.GameData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SqlGameDataAccess implements GameDataAccess {
    String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
              `id` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256) NOT NULL,
              `game` TEXT NOT NULL,
              PRIMARY KEY (`id`)
            );
            """
    };
    private final AuthDataAccess authDataAccess;

    public SqlGameDataAccess(AuthDataAccess authDataAccess) {
        this.authDataAccess = authDataAccess;
        try {
            DatabaseManager.configureDatabase(createStatements);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        HashSet<GameData> allGames = new HashSet<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM games;";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    // while there is another result
                    // add game to collection
                    while (rs.next()) {
                        int resGameID = rs.getInt(1);
                        String resWhiteUsername = rs.getString(2);
                        String resBlackUsername = rs.getString(3);
                        String resGameName = rs.getString(4);

                        String jsonGame = rs.getString(5);
                        ChessGame resGame = new Gson().fromJson(jsonGame, ChessGame.class);

                        GameData game = new GameData(resGameID, resWhiteUsername, resBlackUsername, resGameName, resGame);
                        allGames.add(game);
                    }
                    return allGames;
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public int createGame(String gameName) throws DataAccessException {
        ChessGame newGame = new ChessGame();
        newGame.getBoard().resetBoard();
        String jsonGame = new Gson().toJson(newGame);

        try (var conn = DatabaseManager.getConnection()) {
            String statement = "INSERT INTO games (whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?);";
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                ps.setString(1, null);
                ps.setString(2, null);
                ps.setString(3, gameName);
                ps.setString(4, jsonGame);

                ps.executeUpdate();
                try (var rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
        return -1;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM games WHERE id=?;";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    int resGameID;
                    String resWhiteUsername;
                    String resBlackUsername;
                    String resGameName;
                    ChessGame resGame;
                    if (rs.next()) {
                        resGameID = rs.getInt("id");
                        resWhiteUsername = rs.getString("whiteUsername");
                        resBlackUsername = rs.getString("blackUsername");
                        resGameName = rs.getString("gameName");

                        String resGameJson = rs.getString("game");
                        resGame = new Gson().fromJson(resGameJson, ChessGame.class);

                        return new GameData(resGameID, resWhiteUsername, resBlackUsername, resGameName, resGame);
                    }
                    return null;
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void addPlayer(String clientColor, int gameID, String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String statement = null;
            if (clientColor.equals("WHITE")) {
                statement = "UPDATE games SET whiteUsername=? WHERE id=?";
            } else if (clientColor.equals("BLACK")) {
                statement = "UPDATE games SET blackUsername=? WHERE id=?";
            }
            try (var ps = conn.prepareStatement(statement)) {
                AuthData user = this.authDataAccess.getAuth(authToken);
                ps.setString(1, user.username());
                ps.setInt(2, gameID);

                if (getGame(gameID) != null) {
                    ps.executeUpdate();
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void clearGames() throws DataAccessException {
        var statement = "TRUNCATE table games;";
        try (var conn = DatabaseManager.getConnection()) {
            try (var  ps = conn.prepareStatement(statement)) {
                ps.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
