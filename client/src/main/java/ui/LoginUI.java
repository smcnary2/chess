package ui;

public class LoginUI {
    private State state = State.SIGNEDOUT;



    //prelogin UI
    //quit
    public void quit(){

    }
    //login
    public void login(){

    }
    //register
    public void register(){

    }

    //postlogin UI
    //logout
    public void logout(){

    }
    //create game
    //list games
    //join games
    //join observer

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
