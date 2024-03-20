package ui;

import server.Server;
public class LoginUI {
    private State state = State.SIGNEDOUT;
    private Server server;

    public LoginUI(){
        server = new Server();
        server.run(8080);
    }
//    public String eval(String input) {
//        var tokens = input.toLowerCase().split(" ");
//        var cmd = (tokens.length > 0) ? tokens[0] : "help";
//        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
//        return switch (cmd) {
//            case "login" -> login(params);
//            case "register" -> register(params);
//            case "list" -> listGames();
//            case "logout" -> logout();
//            case "create" -> createGame(params);
//            case "join" -> joinGame(params);
//            case "observe" -> observerGame(params);
//            case "quit" -> "quit";
//            default -> help();
//        };//should I throw something here
//    }

    //prelogin UI

    //login
//    public String login(String... params){
//
//    }
//    //register
//    public String register(String... params){
//        if(params.length >= 3){
//          var method = params[0];
//          var url = params[1];
//          var body = params[2];
//
//          HttpURLConnection http = sendRequest(url,method,body);
//        }
//    }

    //postlogin UI
    //logout
//    public String logout(){
//
//    }
//    //create game
//    public String createGame(String... params){
//
//    }
    //list games
//    public String listGames(){
//
//    }
//    //join games
//    public String joinGame(String... params){
//
//    }
//    //join observer
//    public String observerGame(String... params){
//
//    }

    public String help() {
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
