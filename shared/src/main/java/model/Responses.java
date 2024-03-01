package model;

import java.util.List;

public class Responses {

    public AuthData registerResponse(AuthData userAndToken) {//success response: { "username":"", "authToken":"" }
        return userAndToken;
    }

    public AuthData loginResponse(AuthData userAndToken) {

        return userAndToken;
    }

    public List<WebGame> listGameResponse(List<WebGame> listOfGames) {//need to finish
        return listOfGames;
    }

    public static WebGame newGameResponse(WebGame webGame) {
        return webGame;
    }
    public ErrorMessage message(ErrorMessage e){
        return e;
    }

}
