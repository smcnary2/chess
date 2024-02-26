package model;

public class AuthData {
    String authToken;
    String username;

    public AuthData(String token, String name) {
        username = name;
        authToken = token;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }
}
