package dataAccess;

import dataAccess.interfaces.UserDataAccess;
import model.UserData;

import java.sql.SQLException;

public class SqlUserDataAccess implements UserDataAccess {

    String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS users (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
            );
            """
    };

    public SqlUserDataAccess() {
        try {
            DatabaseManager.configureDatabase(createStatements);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM users WHERE username=?;";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    String resUsername;
                    String resPassword;
                    String resEmail;
                    if (rs.next()) {
                        resUsername = rs.getString("username");
                        resPassword = rs.getString("password");
                        resEmail = rs.getString("email");
                    } else {
                        return null;
                    }

                    return new UserData(resUsername, resPassword, resEmail);
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void createUser(String username, String password, String email) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?);";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, email);

                ps.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void clearUsers() throws DataAccessException {
        var statement = "TRUNCATE table users;";
        try (var conn = DatabaseManager.getConnection()) {
            try (var  ps = conn.prepareStatement(statement)) {
                ps.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
