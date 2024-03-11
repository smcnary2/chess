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
        GameService gameService = null;
        try {
            userService = new UserService();
            gameService = new GameService();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }


        GameService finalGameService = gameService;
        UserService finalUserService = userService;

        //clear Request


        Spark.delete("/db", (request, response) -> new Handlers().clearHandler(request, response, finalUserService, finalGameService));//not complete
        // Register your endpoints and handle exceptions here.
        Spark.post("/user", (request, response) -> new Handlers().registerHandler(request, response, finalUserService));
        //login:get?

        Spark.post("/session", ((request, response) -> new Handlers().loginHandler(request, response, finalUserService)));
        //logout:delete
        Spark.delete("/session", ((request, response) -> new Handlers().logoutHandler(request, response, finalUserService)));//works now

        //list games: get
        Spark.get("/game", (request, response) -> new Handlers().listGameHandler(request, response,finalUserService, finalGameService));

        //new game
        Spark.post("/game", ((request, response) -> new Handlers().newGameHandler(request, response, finalUserService, finalGameService)));//works
        //join game
        Spark.put("/game", (request, response) -> new Handlers().joinGameHandler(request,response,finalUserService,finalGameService));//working on it


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
