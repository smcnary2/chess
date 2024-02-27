package model;

public class UserRequests {
    private String username;
    private String password;
    private String email;
    public String authtoken;
    public String gameName;
    public int error;
    public UserRequests (String n, String pw, String e){//register
        username = n;
        password = pw;
        email = e;
        error = 200;
    }
    public UserRequests(){//clear
        error = 200;
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

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
