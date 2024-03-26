package clientTests;

import dataAccess.DataAccessException;
import model.User;
import model.UserRequests;
import model.WebGame;
import org.junit.jupiter.api.*;
import server.Server;
import service.GameService;
import service.UserService;
import ui.ServerFacade;


public class ServerFacadeTests {

    private static Server server;
    public static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade("http://localhost:"+port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void clear() throws DataAccessException {
        var userService = new UserService();
        var gameService = new GameService();
        userService.clear();
        gameService.clear();
    }
    @Test
    public void UserServiceTests() throws DataAccessException {

        //register
        User newuser = new User("james","password","email");
        var result = serverFacade.register(newuser);
        Assertions.assertEquals("james",result.getUsername());
        //logout
        serverFacade.logout(result);
        Assertions.assertNull(new UserService().pushToAuthDAO.findAuth(result.getAuthToken()));
        //login
        User newUser2 = new User("james", "password");
        var result2 = serverFacade.login(newUser2);
        Assertions.assertEquals(new UserService().pushToAuthDAO.findAuth(result2.getAuthToken()).getUsername(), result2.getUsername());
    }
    @Test
    public void GameServiceTests() throws DataAccessException {
        User newuser = new User("james","password","email");
        var authdata = serverFacade.register(newuser);
        //createGame
        WebGame newgame1 = new WebGame("newgame");
        WebGame newgame2 = new WebGame("newgame2");
        var resultgame1 = serverFacade.creategame(authdata.getAuthToken(), newgame1);
        var resultgame2 = serverFacade.creategame(authdata.getAuthToken(), newgame2);
        Assertions.assertNotNull(new GameService().pushRequest.findGame(resultgame1.getGameID()));
        Assertions.assertNotNull(new GameService().pushRequest.findGame(resultgame2.getGameID()));
        //listgames
        var resultList = serverFacade.listgames(authdata.getAuthToken());
        Assertions.assertEquals(2,resultList.length);
        //joinGame
        UserRequests gamereq = new UserRequests("BLACK",resultgame1.getGameID()) ;
        serverFacade.joinGame(authdata.getAuthToken(), gamereq);
        Assertions.assertEquals(new GameService().pushRequest.findGame(resultgame1.getGameID()).getBlackUsername(),"james");
        //observeGame
        UserRequests gamereq2 = new UserRequests(resultgame1.getGameID()) ;
        serverFacade.joinGame(authdata.getAuthToken(), gamereq);

    }




}
