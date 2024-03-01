package server;

import handlers.Handlers;
import service.GameService;
import service.UserService;
import spark.Spark;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        UserService userService = new UserService();
        GameService gameService = new GameService();



        //clear Request
        Spark.delete("/db", (request, response) -> new Handlers().clearHandler(request, response, userService,gameService));//not complete
        // Register your endpoints and handle exceptions here.
        Spark.post("/user", (request, response) -> new Handlers().registerHandler(request, response, userService));
        //login:get?
        Spark.post("/session", ((request, response) -> new Handlers().loginHandler(request, response, userService)));
        //logout:delete
        Spark.delete("/session", ((request, response) -> new Handlers().logoutHandler(request, response, userService)));//works now

        //list games: get
        Spark.get("/game", (request, response) -> new Handlers().listGameHandler(request, response,userService, gameService));

        //new game
        Spark.post("/game", ((request, response) -> new Handlers().newGameHandler(request, response, userService, gameService)));//works
        //join game
        Spark.put("/game", (request, response) -> new Handlers().joinGameHandler(request,response,userService,gameService));//working on it


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
