package dataAccess;

import com.google.gson.Gson;
import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AuthDAO extends createDatabase{
    public static Map<String, AuthData> token = new HashMap<>();
    public AuthDAO() throws DataAccessException {
    }
    //find authorization
    public AuthData findAuth(String auth) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authtoken, json FROM authChess WHERE authtoken = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, auth);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to configure database");
        }
        return null;
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        var AuthData = new Gson().fromJson(json, AuthData.class);
        return AuthData;
    }

    public void delete(String user) throws DataAccessException {
        // remove key or replace the authorization?
        var statement = "DELETE FROM authChess WHERE authToken = ?";

        executeUpdate(statement, user);
    }

    public void clear() throws DataAccessException {
        //deletes all instances
        var statement = "TRUNCATE authChess";
        executeUpdate(statement);

    }

    //delete, insert, clear
    public void insert(AuthData t) throws DataAccessException {
        var statement = "INSERT INTO authChess (authToken, username, json) VALUES (?, ?, ?)";
        var json = new Gson().toJson(t);
        executeUpdate(statement, t.getAuthToken(), t.getUsername(), json);
    }

}
