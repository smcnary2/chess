package serviceTests;

import dataAccess.DataAccessException;
import model.UserRequests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.GameService;
import service.UserService;


public class UserServiceTests {
    @Test
    void clearUserDAO() throws DataAccessException {
        var userService = new UserService();
        var newUser = new UserRequests("joe", "pw", "joe@joe.com");
        userService.registerUser(newUser);

        userService.clear();
        Assertions.assertNotNull(userService.pushToUserDAO.findAllUsers());

    }

    @Test
    void registerUser() {
        var userService = new UserService();
        var req = new UserRequests("joe", "pw", "joe@joe.com");

        var res = Assertions.assertDoesNotThrow(() -> userService.registerUser(req));

        Assertions.assertEquals("joe", res.getUsername());

    }

    @Test
    void resisterUserInvalidReq() {
        var userService = new UserService();
        var req = new UserRequests("joe", null, "joe@joe.com");

        var res = Assertions.assertDoesNotThrow(() -> userService.registerUser(req));

        Assertions.assertEquals("joe", res.getUsername());
    }


    @Test
    void loginUser(){
        var userService = new UserService();
        var createUser = new UserRequests("joe", "pw", "joe@joe.com");
        Assertions.assertDoesNotThrow(() -> userService.registerUser(createUser));
        userService.pushToAuthDAO.delete(createUser.getUser());


        var req = new UserRequests("joe", "pw");
        var res = Assertions.assertDoesNotThrow(() -> userService.login(req));

        Assertions.assertEquals("joe", res.getUsername());
    }
    @Test
    void loginInvalidReq(){
        var userService = new UserService();
        var createUser = new UserRequests("joe", "pw", "joe@joe.com");
        Assertions.assertDoesNotThrow(() -> userService.registerUser(createUser));
        userService.pushToAuthDAO.delete(createUser.getUser());

        var req = new UserRequests("joe", null);
        var res = Assertions.assertDoesNotThrow(() -> userService.login(req));

        Assertions.assertNull(res);
    }
    @Test
    void loginWrongPw(){
        var userService = new UserService();
        var createUser = new UserRequests("joe", "pw", "joe@joe.com");
        Assertions.assertDoesNotThrow(() -> userService.registerUser(createUser));
        userService.pushToAuthDAO.delete(createUser.getUser());

        var req = new UserRequests("joe", "pw1");
        var res = Assertions.assertDoesNotThrow(() -> userService.login(req));

        Assertions.assertNull(res);
    }

    @Test
    void logoutUser(){
        var userService = new UserService();
        var createUser = new UserRequests("joe", "pw", "joe@joe.com");
        var token = Assertions.assertDoesNotThrow(() -> userService.registerUser(createUser));

        var req = new UserRequests();
        req.setAuthtoken(token.getAuthToken());
        Assertions.assertDoesNotThrow(() -> userService.logout(req));

        Assertions.assertNull(Assertions.assertDoesNotThrow(() -> userService.pushToAuthDAO.findAuth(createUser.getUser())));
    }

    @Test
    void createGame(){
        var userService = new UserService();
        var createUser = new UserRequests("joe", "pw", "joe@joe.com");
        var token = Assertions.assertDoesNotThrow(() -> userService.registerUser(createUser));


        var gameService = new GameService();
        var req = new UserRequests("newgame");
        if(userService.verifyAuth(token.getAuthToken()) !=null){
            req.error = 200;
        }
        req.setAuthtoken(token.getAuthToken());
        Assertions.assertNotNull(Assertions.assertDoesNotThrow(() -> gameService.newGame(req)));
    }
    @Test
    void listgames(){
        var userService = new UserService();
        var createUser = new UserRequests("joe", "pw", "joe@joe.com");
        var token = Assertions.assertDoesNotThrow(() -> userService.registerUser(createUser));

        var gameService = new GameService();
        var createGame = new UserRequests("newgame");
        if(userService.verifyAuth(token.getAuthToken()) !=null){
            createGame.error = 200;
        }
        createGame.setAuthtoken(token.getAuthToken());
        Assertions.assertDoesNotThrow(() -> gameService.newGame(createGame));

        var req = new UserRequests();
        req.setAuthtoken(token.getAuthToken());
        Assertions.assertNotNull(Assertions.assertDoesNotThrow(() -> gameService.listGames(req)));

        var createGame2 = new UserRequests("newgame2");
        createGame2.error = 200;
        createGame.setAuthtoken(token.getAuthToken());
        Assertions.assertDoesNotThrow(() -> gameService.newGame(createGame2));
        var createGame3 = new UserRequests("newgame3");
        createGame3.error = 200;
        createGame.setAuthtoken(token.getAuthToken());
        Assertions.assertDoesNotThrow(() -> gameService.newGame(createGame3));

        var req2 = new UserRequests();
        req2.setAuthtoken(token.getAuthToken());
        Assertions.assertNotNull(Assertions.assertDoesNotThrow(() -> gameService.listGames(req2)),"list multiple games");
    }

    @Test
    void joinGame(){
        var userService = new UserService();
        var createUser = new UserRequests("joe", "pw", "joe@joe.com");
        var token = Assertions.assertDoesNotThrow(() -> userService.registerUser(createUser));

        var createUser2 = new UserRequests("bob","pw2", "bob@bob");
        var token2 = Assertions.assertDoesNotThrow(() -> userService.registerUser(createUser2));


        var gameService = new GameService();
        var createGame = new UserRequests("newgame");
        if(userService.verifyAuth(token.getAuthToken()) !=null){
            createGame.error = 200;
        }
        createGame.setAuthtoken(token.getAuthToken());
        var game = Assertions.assertDoesNotThrow(() -> gameService.newGame(createGame));

        var req = new UserRequests("BLACK", game.getGameID());
        req.setUsername(createUser.getUser());
        Assertions.assertDoesNotThrow(() -> gameService.joinGame(req));
        int index = Assertions.assertDoesNotThrow(() -> gameService.pushRequest.findGame(game.getGameID()));
        Assertions.assertEquals("joe",gameService.pushRequest.listOfGames.get(index).getBlackUsername());

        var req2 = new UserRequests("BLACK", game.getGameID());
        req2.setUsername(createUser2.getUser());
        Assertions.assertDoesNotThrow(() -> gameService.joinGame(req2));
        Assertions.assertEquals("joe",gameService.pushRequest.listOfGames.get(index).getBlackUsername(),"assigned black when already assigned");

    }



}