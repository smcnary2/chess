package model;

import chess.ChessGame;

public class WebGame {
    int gameID;
    String whiteUsername;
    String blackUsername;
    String gameName;
    ChessGame games;

    public WebGame(int id, String gn) {
        games = new ChessGame();// did I do that right
        gameID = id;//random number
        gameName = gn;
        //players join game dont assign colors until DAO

    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public void setBlackUsername(String user) {
        blackUsername = user;
    }

    public int getGameID() {
        return gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public ChessGame getGames() {
        return games;
    }

}
