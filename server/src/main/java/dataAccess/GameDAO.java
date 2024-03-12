package dataAccess;

import com.google.gson.Gson;
import model.WebGame;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class GameDAO {
    public List<WebGame> listOfGames;

    public GameDAO() throws DataAccessException {

        listOfGames = new ArrayList<>();
        initializeDatabase();
    }

    public void insertGame(WebGame newGame) throws DataAccessException {// I'm not sure how claim color works
        var statement = "INSERT INTO gameChess (gameID, game_name , json) VALUES (?,?,?)";
        var json = new Gson().toJson(newGame);
        executeUpdate(statement, newGame.getGameID(), newGame.getGameName(), json);
    }

    public List<WebGame> findAllGames() throws DataAccessException {
        var result = new ArrayList<WebGame>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, game_name, json FROM gamechess";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Unable to read data");
        }
        return result;
    }

    public int joinGameInDAO(String playerColor, int gameID, String username)throws DataAccessException{
        WebGame index = findGame(gameID);
        if( index != null){

            if(playerColor.equals("WHITE")){
                var statement = "UPDATE gameChess SET json = ? WHERE gameID = ? AND white_username IS NULL";
                index.setWhiteUsername(username);
                var json = new Gson().toJson(index);
                executeUpdate(statement, json, gameID);
                statement = "UPDATE gameChess SET white_username = ? WHERE gameID = ? AND white_username IS NULL";
                executeUpdate(statement, username, gameID);
                if(findGame(gameID).getWhiteUsername().equals(username)){
                    return 200;
                }else {
                    return 403;
                }
            } else if (playerColor.equals("BLACK")) {
                index.setBlackUsername(username);
                var json = new Gson().toJson(index);
                var statement = "UPDATE gameChess SET json = ? WHERE gameID = ? AND black_username IS NULL";
                executeUpdate(statement, json, gameID);
                statement = "UPDATE gameChess SET black_username = ? WHERE gameID = ? AND black_username IS NULL";
                executeUpdate(statement, username, gameID);
                if(findGame(gameID).getBlackUsername().equals(username)){
                    return 200;
                }else {
                    return 403;
                }
            }

        }
        return 400;//game doesnt exist
    }
    public int watchGame(int gameID, String username)throws DataAccessException{
        var index = findGame(gameID);
        if( index != null){
            return 200;
        }
        return 400;
    }
    public WebGame findGame(int gameid) throws DataAccessException {// am I supposed to haev this
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, json FROM gameChess WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameid);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data"));
        }
        return null;
    }

    private WebGame readGame(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        var newgame = new Gson().fromJson(json, WebGame.class);
        return newgame;
    }
    public void clearGame() throws DataAccessException {
        var statement = "TRUNCATE gameChess";
        executeUpdate(statement);
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
            ex.printStackTrace();
            throw new DataAccessException("Unable to configure database");
        }

    }

    private final String [] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  gameChess (
              `row` int NOT NULL AUTO_INCREMENT,
              `game_name` varchar(256) NOT NULL,
              `gameID` int NOT NULL,
              `white_username` varchar(256) DEFAULT NULL,
              `black_username` varchar(256) DEFAULT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`row`),
              INDEX(white_username),
              INDEX(black_username),
              INDEX(game_name),
              INDEX(gameID)
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
