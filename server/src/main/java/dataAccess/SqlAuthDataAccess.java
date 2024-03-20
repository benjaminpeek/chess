package dataAccess;

import dataAccess.interfaces.AuthDataAccess;
import model.AuthData;

import java.sql.SQLException;
import java.util.UUID;

public class SqlAuthDataAccess implements AuthDataAccess {
    String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS auths (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
            );
            """
    };

    public SqlAuthDataAccess() {
        try {
            DatabaseManager.configureDatabase(createStatements);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String createAuth(String username) throws DataAccessException {
        String newAuthToken = UUID.randomUUID().toString();

        try (var conn = DatabaseManager.getConnection()) {
            String statement = "INSERT INTO auths (authToken, username) VALUES (?, ?);";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, newAuthToken);
                ps.setString(2, username);

                ps.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
        return newAuthToken;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "DELETE FROM auths WHERE authToken=?;";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                ps.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, username FROM auths WHERE authToken=?;";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    String resAuthToken = null;
                    String resUsername = null;
                    if (rs.next()) {
                        resAuthToken = rs.getString("authToken");
                        resUsername = rs.getString("username");
                    }

                    return new AuthData(resAuthToken, resUsername);
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void clearAuths() throws DataAccessException {
        var statement = "TRUNCATE table auths;";
        try (var conn = DatabaseManager.getConnection()) {
            try (var  ps = conn.prepareStatement(statement)) {
                ps.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
