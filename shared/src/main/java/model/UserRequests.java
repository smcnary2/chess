package model;

public class UserRequests {
    private String username;
    private String password;
    private String email;
    public String authtoken;
    public String gameName;
    public String playerColor;
    public int gameID;



    public int error;
    public UserRequests (String n, String pw, String e){//register
        username = n;
        password = pw;
        email = e;
        error = 200;
    }
    public UserRequests(String u, String pw){
        username = u;
        password = pw;
        error = 200;
    }
    public UserRequests(){//clear
        error = 200;
    }

    public UserRequests(String newgame) {
        gameName = newgame;
    }

    public UserRequests(String color, int gameID) {
        playerColor = color;
        this.gameID = gameID;
        error = 200;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    //getters and setters
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUser() {
        return username;
    }
    public String getGameName() {
        return gameName;
    }
    public String getAuthtoken() {
        return authtoken;
    }
    public int getGameID() {
        return gameID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
