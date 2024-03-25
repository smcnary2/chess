package dataAccess;

import com.google.gson.Gson;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO extends createDatabase{

    public UsersDAO() throws DataAccessException {
        new createDatabase();
    }
    public void insertUser(User newUser) throws DataAccessException {// insert user
        var statement = "INSERT INTO usersChess (username, password, email, json) VALUES(?,?,?,?)";
        var json = new Gson().toJson(newUser);
        Object id = executeUpdate(statement,newUser.username, newUser.password, newUser.email, json);
        //System.out.print(id);

    }
    public void clearAllUsers() throws DataAccessException {
        var statement = "TRUNCATE usersChess";
        executeUpdate(statement);

    }
    public List<User> findAllUsers() throws DataAccessException {
        var result = new ArrayList<User>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, json FROM usersChess";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readUser(rs));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to read data");
        }
        return result;
    }

    public User findUser(User newUser) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, json FROM usersChess WHERE username = ? AND password = ?";
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
        //System.out.print(user);
        return user;
    }



}
