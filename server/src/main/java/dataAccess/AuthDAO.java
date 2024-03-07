package dataAccess;

import com.google.gson.Gson;
import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class AuthDAO {
    public static Map<String, AuthData> token = new HashMap<>();

    //find authorization
    public AuthData findAuth(String auth) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id, json FROM pet WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, auth);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Unable to configure database");
        }
        return null;
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var id = rs.getInt("id");
        var json = rs.getString("json");
        var AuthData = new Gson().fromJson(json, AuthData.class);
        return AuthData;
    }

    public void delete(String user) {
        token.remove(user);
        // remove key or replace the authorization?
    }

    public void clear() {
        //deletes all instances
        token.clear();

    }

    //delete, insert, clear
    public void insert(AuthData t) throws DataAccessException {
        var statement = "INSERT INTO authChess (authToken, username, json) VALUES (?, ?, ?)";
        var json = new Gson().toJson(t);
        executeUpdate(statement, t.getAuthToken(), t.getUsername(), json);
    }

    public boolean isEmpty(){
        return token.isEmpty();
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException{
        try(var conn = DatabaseManager.getConnection()){
            try(var prepStmt = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)){
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) prepStmt.setString(i + 1, p);
                    else if (param instanceof Integer p) prepStmt.setInt(i + 1, p);
                    else if (param == null) prepStmt.setNull(i + 1, NULL);
                }
                prepStmt.executeUpdate();

                var rs = prepStmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        }catch (SQLException ex){
            throw new DataAccessException("Unable to configure database");
        }

    }

    private final String [] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authChess (
              `row` int NOT NULL AUTO_INCREMENT,
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`row`),
              INDEX(username),
              INDEX(authToken)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void initializeDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()){
            for(var statement : createStatements) {
                try (var prepStmt = conn.prepareStatement(statement)) {
                    prepStmt.executeUpdate();
                }
            }
        }catch (SQLException ex){
            throw new DataAccessException("Unable to configure database");
        }
    }

}
