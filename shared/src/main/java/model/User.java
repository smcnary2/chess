package model;

public class User {
    private String username;
    private String password;
    private String email;

    public User(String n, String pw, String e) {
        username = n;
        password = pw;
        email = e;
    }

    public User(String n, String pw) {
        username = n;
        password = pw;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
