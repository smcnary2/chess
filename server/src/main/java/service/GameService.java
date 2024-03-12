package service;

import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.UserRequests;
import model.WebGame;

import java.util.List;
import java.util.UUID;

public class GameService {
    public GameDAO pushRequest;
    public GameService() throws DataAccessException {
        pushRequest = new GameDAO();
    }

    public List<WebGame> listGames(UserRequests newrequest) throws DataAccessException {
        //can't add auth check until you fix listGamesReq
        if(newrequest.error ==200) {
            return pushRequest.findAllGames();
        }
        return null;
    }
    public WebGame newGame(UserRequests requests) throws DataAccessException{
        if(requests.error == 200){
            WebGame ngame = new WebGame(Math.abs(UUID.randomUUID().hashCode()), requests.getGameName());// dont use color here it's set in join game
            pushRequest.insertGame(ngame);
            return ngame;
        }
        return null;
    }
    public void joinGame(UserRequests requests)throws DataAccessException{
        int errorcode;
        if(requests.getPlayerColor() != null) {
            errorcode = pushRequest.joinGameInDAO(requests.getPlayerColor(), requests.getGameID(), requests.getUser());
        }else{
            errorcode = pushRequest.watchGame(requests.getGameID(), requests.getUser());
        }

        requests.error = errorcode;

    }
    public void clear()throws DataAccessException{
        pushRequest.clearGame();
    }
}
