package dataAccess;

import model.WebGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

}
