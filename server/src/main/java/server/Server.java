package server;

import handlers.Handlers;
import service.UserService;
import spark.Spark;

public class Server {
    public static void main(String[] args) {
        new Server().run(8080);
    }
    public int run(int desiredPort) {
        Spark.port(desiredPort);
        UserService userService = new UserService();
        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", (request, response) -> new Handlers().registerHandler(request, response, userService));
        //clear Request
        Spark.delete("/db", (request, response) -> new Handlers().clearHandler(request, response, userService));//not complete

        //join game
        //Spark.post("/game", (request, response) -> new JoinGameHandler());//working on it
        //list games: get
        //Spark.get("/game", (request, response) -> new ListGamesHandler().handle(request, response));
        //login:get?
        Spark.post("/session", ((request, response) -> new Handlers().loginHandler(request, response, userService)));
        //new game
        //Spark.post("/game", ((request, response) -> new NewGameHandler().handle(request, response)));//works
        //logout:delete
        Spark.delete("/session", ((request, response) -> new Handlers().logoutHandler(request, response, userService)));//works now


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
