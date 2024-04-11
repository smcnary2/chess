package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import webSocketMessages.userCommands.UserGameCommand;

public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch(action.getCommandType()){
            case LEAVE -> leave();
            case RESIGN -> resign();
            case MAKE_MOVE -> makeMove();
            case JOIN_PLAYER -> joinPlayer();
            case JOIN_OBSERVER -> joinObserver();
            case null -> ;

        }
    }
    private void leave(int gameID){

    }
    private void resign(int gameID){
        //remove playerName
        //format message
        //broadcast
    }
    private void makeMove(int gameID, ChessMove move){}
    private void joinPlayer(int gameID, ChessGame.TeamColor color){
        //addplayer name to connection

    }
    private void joinObserver(int gameID){
        //add player name to connection
        //format message
        //broadcast
    }
}
