package model;

public class Responses {
    public Responses(){

    }
    public String returnMessage(int error) {
        if (error == 200) {
            return "request approved";
        }
        return "request failed";
    }
    public AuthData registerResponse(AuthData userAndToken) {//success response: { "username":"", "authToken":"" }
        //System.out.println(userAndToken.getAuthToken());
        return userAndToken;

    }

    public AuthData loginResponse(AuthData userAndToken) {
        System.out.println(userAndToken.getAuthToken());
        return userAndToken;
    }
}
