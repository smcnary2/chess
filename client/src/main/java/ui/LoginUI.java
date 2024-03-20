package ui;

import model.User;

import java.util.Arrays;

public class LoginUI {
    private static State state = State.SIGNEDOUT;
    private static ServerFacade server;

    public LoginUI(String serverUrl){
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
        }catch(Exception e){
            e.printStackTrace();
        }
        return input;
    }

    //prelogin UI

    //login
    public static String login(String... params){
        return "error";
    }
    //register
    public static String register(String... params){
        if(params.length >= 3){
            User newuser = new User(params[0],params[1],params[2]);
            var result = server.register(newuser);
            return String.format("You just registed as %s",result.getUsername());


        }
        return "error";
    }

    //postlogin UI
    //logout
    public static String logout(){
        return"invalid request";
    }
    //create game
    public static String createGame(String... params){
        return"invalid request";
    }
    //list games
    public static String listGames(){
        return"invalid request";
    }
    //join games
    public static String joinGame(String... params){
        return"invalid request";
    }
    //join observer
    public static String observerGame(String... params){
        return"invalid request";
    }

    public static String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - login <USERNAME> <PASSWORD>
                    - register <USERNAME> <PASSWORD> <EMAIL>
                    - quit
                    - help
                    """;
        }
        return """
                - list
                - create <NAMES>
                - join <ID> [WHITE|BLACK|<empty>]
                - observe <ID>
                - logout
                - quit
                - help
                """;
    }


}
