package dataAccess;

import model.WebGame;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class GameDAO {
    public List<WebGame> listOfGames;

    public GameDAO() {

        listOfGames = new ArrayList<>();
    }

    public void insertGame(WebGame newGame) throws DataAccessException {// I'm not sure how claim color works
        listOfGames.add(newGame);
    }

    public List<WebGame> findAllGames() throws DataAccessException {
        return listOfGames;// am I supposed to return this formatted
    }

    public int joinGameInDAO(String playerColor, int gameID, String username)throws DataAccessException{
        int index = findGame(gameID);
        if( index != -1){
            if(Objects.equals(listOfGames.get(index).getWhiteUsername(), null)&&playerColor.equals("WHITE")){
                listOfGames.get(index).setWhiteUsername(username);
                return 200;
            } else if (Objects.equals(listOfGames.get(index).getBlackUsername(), null) &&playerColor.equals("BLACK")) {
                listOfGames.get(index).setBlackUsername(username);
                return 200;
            }else{
                return 403;//observer not quite so sure what to put here
            }

        }
        return 400;//game doesnt exist
    }
    public int watchGame(int gameID, String username)throws DataAccessException{
        int index = findGame(gameID);
        if( index != -1){
            return 200;
        }
        return 400;
    }
    public int findGame(int gameid) throws DataAccessException {// am I supposed to haev this
        for (int i = 0; i < listOfGames.size(); i++) {
            if (listOfGames.get(i).getGameID() == (gameid)) {
                return i;
            }
        }
        return -1;
    }
    public void clearGame() throws DataAccessException {
        listOfGames = new ArrayList<>();
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
