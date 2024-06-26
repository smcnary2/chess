package ui;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthData;
import model.User;
import model.UserRequests;
import model.WebGame;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static ui.GamePlayUI.drawChessBoard;

public class LoginUI {
    private static AuthData authdata;
    private static State state = State.SIGNEDOUT;
    private static ServerFacade server;

    public LoginUI(String serverUrl) {
        server = new ServerFacade(serverUrl);
    }

    public static String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
                case "register" -> register(params);
                case "list" -> listGames();
                case "logout" -> logout();
                case "create" -> createGame(params);
                case "join" -> joinGame(params);
                case "observe" -> observerGame(params);
                case "quit" -> "quit";
                default -> help();
            };//should I throw something here
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    //prelogin UI

    //login
    public static String login(String... params) {
        if (params.length >= 2) {
            User newuser = new User(params[0], params[1]);
            AuthData result = server.login(newuser);
            authdata = result;
            state = State.SIGNEDIN;
            return String.format("You just loggedin as %s", result.getUsername());


        }
        return "error";
    }

    //register
    public static String register(String... params) {
        if (params.length >= 3) {
            User newuser = new User(params[0], params[1], params[2]);
            var result = server.register(newuser);
            authdata = result;
            state = State.SIGNEDIN;
            return String.format("You just registed as %s", result.getUsername());


        }
        return "error";
    }

    //postlogin UI
    //logout
    public static String logout() throws DataAccessException {
        assertSignedIn();
        var result = server.logout(authdata);
        state = State.SIGNEDOUT;
        return String.format("%s have been signed out",authdata.getUsername());

    }

    //create game
    public static String createGame(String... params) throws DataAccessException {
        assertSignedIn();
        WebGame newgame = new WebGame(params[0]);
        var result = server.creategame(authdata.getAuthToken(),newgame);
        return String.format("You have created a new game\n game name: %s; game ID: %s",result.getGameName(),result.getGameID());
    }

    //list games
    public static String listGames() throws DataAccessException {
        assertSignedIn();
        var games = server.listgames(authdata.getAuthToken());
        var result = new StringBuilder();
        var gson = new Gson();
        for(var game:games){
            result.append(gson.toJson(game)).append('\n');
        }
        return result.toString();
    }

    //join games
    public static String joinGame(String... params) throws DataAccessException {
        assertSignedIn();
        if(params.length <=2){
            try{
                var id = Integer.parseInt(params[0]);
                var color = params[1].toUpperCase();
                UserRequests gamereq  = new UserRequests(color, id);
                server.joinGame(authdata.getAuthToken(), gamereq);
                var game = getGame(id);
                if(game != null){
                    if(game.getBlackUsername() != null && game.getBlackUsername().equals(authdata.getUsername())){
                        color = "BLACK";
                        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
                        drawChessBoard(out);
                        return String.format("you joined %s as %s",id, color );
                    }else if (game.getWhiteUsername().equals(authdata.getUsername())){
                        color = "WHITE";
                        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
                        drawChessBoard(out);
                        return String.format("you joined %s as %s",id, color );
                    }else{
                        return "the game is full";
                    }
                }else{
                    //add as observer
                    observerGame(params);
                   return String.format("%s is full. You've been added as an observer", id);
                }

            }catch(Exception e){

            }
        }
        return "invalid request";
    }


    //join observer
    public static String observerGame(String... params) throws DataAccessException {
        assertSignedIn();
        int id;
        if(params.length ==1 ||params.length == 2){
            id = Integer.parseInt(params[0]);
            UserRequests game = new UserRequests(id);
            server.joinGame(authdata.getAuthToken(), game);
        }
        return "invalid request";
    }
    public static WebGame getGame(int id){
        for(var game : server.listgames(authdata.getAuthToken())){
            if(game.getGameID() == id){
                return game;
            }
        }
        return null;
    }
    public static String reloadBoard() throws DataAccessException {
        assertSignedIn();
        return " ";
    }
    public static String leave() throws DataAccessException {
        assertSignedIn();
        return " ";
    }
    public static String makeMove() throws DataAccessException {
        assertPlayer();

        return " ";
    }
    public static String Resign() throws DataAccessException {
        assertPlayer();
        return " ";
    }
    public static String highlightMoves() throws DataAccessException {
        assertPlayer();
        return "";
    }


    public static String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - login <USERNAME> <PASSWORD>
                    - register <USERNAME> <PASSWORD> <EMAIL>
                    - quit
                    - help
                    """;
        }if(state == State.SIGNEDIN){
            return """
                - list
                - create <NAMES>
                - join <ID> [WHITE|BLACK|<empty>]
                - observe <ID>
                - logout
                - quit
                - help
                """;
        }if(state == State.PLAYER){
            return """
                    - reload
                    - make
                    - highlight
                    - leave
                    - resign
                    - help
                    """;
        }
        //state observer
        return """
                - redraw chess board
                - leave
                - help
                """;

    }

    private static void assertSignedIn() throws DataAccessException {
        if (state == State.SIGNEDOUT) {
            throw new DataAccessException("you must sign in");
        }
    }
    private static void assertPlayer() throws DataAccessException {
        if (state != State.PLAYER) {
            throw new DataAccessException("You cannot preform this action");
        }
    }
}