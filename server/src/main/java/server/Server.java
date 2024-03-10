package server;

import dataAccess.DataAccessException;
import handlers.Handlers;
import service.GameService;
import service.UserService;
import spark.Spark;

public class Server {

    public int run(int desiredPort)  {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        UserService userService = null;
        try {
            userService = new UserService();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        GameService gameService = new GameService();



        //clear Request
        UserService finalUserService = userService;
        Spark.delete("/db", (request, response) -> new Handlers().clearHandler(request, response, finalUserService,gameService));//not complete
        // Register your endpoints and handle exceptions here.
        Spark.post("/user", (request, response) -> new Handlers().registerHandler(request, response, finalUserService));
        //login:get?

        Spark.post("/session", ((request, response) -> new Handlers().loginHandler(request, response, finalUserService)));
        //logout:delete
        Spark.delete("/session", ((request, response) -> new Handlers().logoutHandler(request, response, finalUserService)));//works now

        //list games: get
        Spark.get("/game", (request, response) -> new Handlers().listGameHandler(request, response,finalUserService, gameService));

        //new game
        Spark.post("/game", ((request, response) -> new Handlers().newGameHandler(request, response, finalUserService, gameService)));//works
        //join game
        Spark.put("/game", (request, response) -> new Handlers().joinGameHandler(request,response,finalUserService,gameService));//working on it


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
