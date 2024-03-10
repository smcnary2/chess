package dataAccess;

import com.google.gson.Gson;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class UsersDAO {


    public UsersDAO() throws DataAccessException {

        initializeDatabase();
    }
    public void insertUser(User newUser) throws DataAccessException {// insert user
        var statement = "INSERT INTO usersChess (username, password, email, json) VALUES(?,?,?,?)";
        var json = new Gson().toJson(newUser);
        Object id = executeUpdate(statement,newUser.username, newUser.password, newUser.email, json);
        System.out.print(id);

    }
    public void clearAllUsers() throws DataAccessException {
        var statement = "TRUNCATE userschess";
        executeUpdate(statement);

    }
    public List<User> findAllUsers() throws DataAccessException {
        var result = new ArrayList<User>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, json FROM userschess";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readUser(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Unable to read data");
        }
        return result;
    }

    public User findUser(User newUser) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, json FROM userschess WHERE username = ? AND password = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, newUser.username);
                ps.setString(2,newUser.password);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to configure database");
        }
        return null;
    }
    private User readUser(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        var  user= new Gson().fromJson(json, User.class);
        System.out.print(user);
        return user;
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
            CREATE TABLE IF NOT EXISTS  usersChess (
              `row` int NOT NULL AUTO_INCREMENT,
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`row`),
              INDEX(password),
              INDEX(email)
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
