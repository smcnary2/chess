package dataAccess;

import com.google.gson.Gson;
import model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class UsersDAO {
    public List<User> databasePlaceholder;

    public UsersDAO() throws DataAccessException {
        databasePlaceholder= new ArrayList<>();
        initializeDatabase();
    }
    public void insertUser(User newUser) throws DataAccessException {// insert user
        var statement = "INSERT INTO usersChess (username, password, email, json) VALUES(?,?,?,?)";
        var json = new Gson().toJson(newUser);
        Object id = executeUpdate(statement,newUser.username, newUser.password, newUser.email, json);
        System.out.print(id);

    }
    public void clearAllUsers() {
        databasePlaceholder = new ArrayList<>();

    }
    public List<User> findAllUsers() throws DataAccessException {
        return databasePlaceholder;//is this safe
    }

    public User findUser(User newUser) throws DataAccessException {
        for (User x :
                databasePlaceholder) {
            if (x.getUsername().equals(newUser.getUsername())) {
                if (x.getPassword().equals(newUser.getPassword())) {
                    return x;//what am I supposed to return here
                }
            }
        }
        return null;
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
