package server;

import handlers.Handlers;
import spark.Spark;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", (request, response) -> new Handlers().registerHandler(request, response));//do for each end point
        //clear Request
        Spark.delete("/db", (request, response) -> new Handlers().clearHandler(request, response));//not complete
        /*
        //join game
        Spark.post("/game", (request, response) -> new JoinGameHandler());//working on it
        //list games: get
        Spark.get("/game", (request, response) -> new ListGamesHandler().handle(request, response));
        //login:get?
        Spark.post("/session", ((request, response) -> new LoginHandler().handle(request, response)));//does not work
        //new game
        Spark.post("/game", ((request, response) -> new NewGameHandler().handle(request, response)));//works
        //logout:delete
        Spark.delete("/session", ((request, response) -> new LogoutHandler().handle(request, response)));//works now

         */
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
