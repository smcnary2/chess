package model;

import chess.ChessGame;

public class WebGame {
    int gameID;
    String whiteUsername;
    String blackUsername;
    String gameName;
    ChessGame game;

    public WebGame(int id, String gn) {
        game = new ChessGame();// did I do that right
        gameID = id;//random number
        gameName = gn;
        //players join game dont assign colors until DAO

    }

    public int getGameID() {
        return gameID;
    }

    public String getGameName() {
        return gameName;
    }
}
