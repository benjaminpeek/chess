package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.interfaces.AuthDataAccess;
import dataAccess.interfaces.GameDataAccess;
import model.GameData;
import java.sql.SQLException;
import java.util.Collection;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SqlGameDataAccess implements GameDataAccess {
    String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
              `id` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256) NOT NULL,
              `blackUsername` varchar(256) NOT NULL,
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
    public Collection<GameData.SerializedGame> listGames() throws DataAccessException {
        return null;
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

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
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
                    int resGameID = rs.getInt("id");
                    String resWhiteUsername = rs.getString("whiteUsername");
                    String resBlackUsername = rs.getString("blackUsername");
                    String resGameName = rs.getString("gameName");

                    String resGameJson = rs.getString("game");
                    ChessGame resGame = new Gson().fromJson(resGameJson, ChessGame.class);

                    return new GameData(resGameID, resWhiteUsername, resBlackUsername, resGameName, resGame);
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void addPlayer(String clientColor, int gameID, String authToken) throws DataAccessException {

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
