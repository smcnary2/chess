import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UsersDAO;
import model.AuthData;
import model.User;
import model.WebGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class dataAccessTests {
    UsersDAO userdao;
    GameDAO gamedao ;
    AuthDAO authdao ;

    public dataAccessTests() throws DataAccessException {
        userdao = new UsersDAO();
        gamedao = new GameDAO();
        authdao = new AuthDAO();
    }

    //insertuser
    @BeforeEach
    void setup() throws DataAccessException {

        userdao.clearAllUsers();
        gamedao.clearGame();
        authdao.clear();
        
    }
    @Test
    void insertUserTest() throws DataAccessException {

        Assertions.assertDoesNotThrow(()->userdao.insertUser(new User("joe","pw","joe@joe")));
        Assertions.assertEquals("joe", userdao.findUser(new User("joe","pw")).username);
    }

    @Test
    void InsertMoreUserTest() throws DataAccessException {

        Assertions.assertDoesNotThrow(()->userdao.insertUser(new User("joe","pw","joe@joe")));
        Assertions.assertDoesNotThrow(()->userdao.insertUser(new User("steve","pw2","steve@steve")));
        Assertions.assertDoesNotThrow(()->userdao.insertUser(new User("bob","pw3","bob@bob")));
        Assertions.assertEquals("joe", userdao.findUser(new User("joe","pw")).username);
        Assertions.assertEquals(3,userdao.findAllUsers().size());
    }
    //finduser
    @Test
    void findUserTest()throws DataAccessException{

        userdao.insertUser(new User("joe","pw","joe@joe"));
        Assertions.assertEquals("joe",  Assertions.assertDoesNotThrow(()->userdao.findUser(new User("joe","pw")).username));
    }
    @Test
    void invalidfindUserTest() throws DataAccessException {

        userdao.insertUser(new User("joe","pw","joe@joe"));
        Assertions.assertNull(Assertions.assertDoesNotThrow(()->userdao.findUser(new User("mary","pw"))));
    }

    //clearAllUsers
    @Test
    void clearUsers() throws DataAccessException {

        userdao.insertUser(new User("joe","pw","joe@joe"));
        userdao.insertUser(new User("mary","pw2","mary@mary"));

        Assertions.assertNotNull(userdao.findAllUsers());
        Assertions.assertEquals(2, userdao.findAllUsers().size(),"returned wrong amount");
    }
    //findAllUsers
    @Test
    void findAllUsersTest() throws DataAccessException {

        userdao.insertUser(new User("joe","pw","joe@joe"));
        Assertions.assertEquals("joe",  Assertions.assertDoesNotThrow(()->userdao.findAllUsers().getFirst().username));
        Assertions.assertEquals(1, userdao.findAllUsers().size(),"returned wrong amount");

    }
    @Test
    void FindMoreUsersTest() throws DataAccessException {

        userdao.insertUser(new User("joe","pw","joe@joe"));
        userdao.insertUser(new User("mary","pw2","mary@mary"));
        userdao.insertUser(new User("bob", "pw3", "bob@bob"));
        userdao.insertUser(new User("steve","pw4", "steve@steve"));
        Assertions.assertEquals("joe",  Assertions.assertDoesNotThrow(()->userdao.findAllUsers().getFirst().username));
        Assertions.assertEquals(4, userdao.findAllUsers().size(),"returned wrong amount");
    }


    //insertGame
    @Test
    void insertGameTest() throws DataAccessException {

        Assertions.assertDoesNotThrow(()-> gamedao.insertGame(new WebGame(1234,"newgame")));
        Assertions.assertEquals("newgame", gamedao.findGame(1234).getGameName());
    }
    @Test
    void insertMoreGamesTest() throws DataAccessException {

        Assertions.assertDoesNotThrow(()-> gamedao.insertGame(new WebGame(1234,"newgame")));
        Assertions.assertDoesNotThrow(()-> gamedao.insertGame(new WebGame(1111,"newgame2")));
        Assertions.assertDoesNotThrow(()-> gamedao.insertGame(new WebGame(5678,"newgame3")));
        Assertions.assertEquals("newgame", gamedao.findGame(1234).getGameName());
        Assertions.assertEquals("newgame2", gamedao.findGame(1111).getGameName());
    }
    //findAllGames
    @Test
    void findAllGameTest() throws DataAccessException {

        gamedao.insertGame(new WebGame(1234,"newgame"));

        Assertions.assertEquals("newgame",Assertions.assertDoesNotThrow(()-> gamedao.findGame(1234).getGameName()));


    }
    @Test
    void findMoreAllGamesTest() throws DataAccessException {


        gamedao.insertGame(new WebGame(1234,"newgame"));
        gamedao.insertGame(new WebGame(1111,"newgame2"));
        gamedao.insertGame(new WebGame(5678,"newgame3"));

        Assertions.assertEquals("newgame", Assertions.assertDoesNotThrow(()->gamedao.findAllGames().getFirst().getGameName()));
        Assertions.assertEquals(3,Assertions.assertDoesNotThrow(()-> gamedao.findAllGames().size()));
    }

    //joinGameInDAO
    @Test
    void joinGameTest() throws DataAccessException {

        gamedao.insertGame(new WebGame(1234,"newgame"));
        Assertions.assertDoesNotThrow(()->gamedao.joinGameInDAO("WHITE",1234,"username"));

        Assertions.assertEquals("username",gamedao.findGame(1234).getWhiteUsername());
    }

    @Test
    void joinMoreGames() throws DataAccessException {

        gamedao.insertGame(new WebGame(1234,"newgame"));
        Assertions.assertDoesNotThrow(()->gamedao.joinGameInDAO("WHITE",1234,"username"));
        Assertions.assertDoesNotThrow(()->gamedao.joinGameInDAO("BLACK",1234,"username2"));
        Assertions.assertEquals("username",gamedao.findGame(1234).getWhiteUsername());
        Assertions.assertEquals("username2",gamedao.findGame(1234).getBlackUsername());
    }

    //findGame
    @Test
    void findGameTest() throws DataAccessException {

        gamedao.insertGame(new WebGame(1234,"newgame"));

        Assertions.assertEquals("newgame",Assertions.assertDoesNotThrow(()-> gamedao.findGame(1234).getGameName()));


    }
    @Test
    void findMoreGamesTest() throws DataAccessException {

        gamedao.insertGame(new WebGame(1234,"newgame"));
        gamedao.insertGame(new WebGame(1111,"newgame2"));
        gamedao.insertGame(new WebGame(5678,"newgame3"));

        Assertions.assertEquals("newgame", Assertions.assertDoesNotThrow(()->gamedao.findGame(1234).getGameName()));
        Assertions.assertEquals("newgame2",Assertions.assertDoesNotThrow(()-> gamedao.findGame(1111).getGameName()));
    }
    //clearGame
    @Test
    void clearGameTest() throws DataAccessException {

        gamedao.insertGame(new WebGame(1234,"newgame"));
        gamedao.insertGame(new WebGame(1111,"newgame2"));
        gamedao.insertGame(new WebGame(5678,"newgame3"));

        Assertions.assertDoesNotThrow(gamedao::clearGame);
        Assertions.assertNull(gamedao.findGame(1234));
        Assertions.assertNull(gamedao.findGame(5678));
    }

    //findAuth
    @Test
    void findAuthTest() throws DataAccessException {

        authdao.insert(new AuthData("5005", "joe"));
        Assertions.assertEquals("joe",Assertions.assertDoesNotThrow(()->authdao.findAuth("5005").getUsername()));

    }
    @Test
    void findMultipleAuthTest() throws DataAccessException {

        authdao.insert(new AuthData("5005", "joe"));
        authdao.insert(new AuthData("5006", "mary"));
        authdao.insert(new AuthData("5007", "steve"));

        Assertions.assertEquals("joe",Assertions.assertDoesNotThrow(()->authdao.findAuth("5005").getUsername()));
        Assertions.assertEquals("mary",Assertions.assertDoesNotThrow(()->authdao.findAuth("5006").getUsername()));
        Assertions.assertEquals("steve",Assertions.assertDoesNotThrow(()->authdao.findAuth("5007").getUsername()));
    }
    //delete
    @Test
    void deleteAuthTest() throws DataAccessException {

        authdao.insert(new AuthData("5005", "joe"));
        Assertions.assertDoesNotThrow(()->authdao.delete("5005"));
        Assertions.assertNull(authdao.findAuth("5005"));
    }
    @Test
    void deleteMultAuthTest() throws DataAccessException {

        authdao.insert(new AuthData("5005", "joe"));
        authdao.insert(new AuthData("5006", "mary"));
        authdao.insert(new AuthData("5007", "steve"));

        Assertions.assertDoesNotThrow(()->authdao.delete("5005"));
        Assertions.assertDoesNotThrow(()->authdao.delete("5007"));

        Assertions.assertNull(authdao.findAuth("5005"));
        Assertions.assertNull(authdao.findAuth("5007"));
    }
    //clear
    @Test
    void clearAuthTest() throws DataAccessException {

        authdao.insert(new AuthData("5005", "joe"));
        authdao.insert(new AuthData("5006", "mary"));
        authdao.insert(new AuthData("5007", "steve"));

        Assertions.assertDoesNotThrow(authdao::clear);
        Assertions.assertNull(authdao.findAuth("5005"));
        Assertions.assertNull(authdao.findAuth("5007"));
    }
    //insert
    @Test
    void insertAuthTest() throws DataAccessException {

        Assertions.assertDoesNotThrow(()->authdao.insert(new AuthData("5005", "joe")));
        Assertions.assertEquals("joe",authdao.findAuth("5005").getUsername());

    }
    @Test
    void insertMultipleAuthTest() throws DataAccessException {

        Assertions.assertDoesNotThrow(()->authdao.insert(new AuthData("5005", "joe")));
        Assertions.assertDoesNotThrow(()->authdao.insert(new AuthData("5006", "mary")));
        Assertions.assertDoesNotThrow(()->authdao.insert(new AuthData("5007", "steve")));

        Assertions.assertEquals("joe",authdao.findAuth("5005").getUsername());
        Assertions.assertEquals("mary",authdao.findAuth("5006").getUsername());
        Assertions.assertEquals("steve",authdao.findAuth("5007").getUsername());
    }

}
