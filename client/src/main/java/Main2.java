import ui.Main;

public class Main2 {
    public static void main(String[] args){

        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }
        new Main(serverUrl).run();
    }
}
