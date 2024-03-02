package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthData;
import model.ErrorMessage;
import model.Responses;
import model.UserRequests;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class Handlers {
    private Gson gson = new Gson();
    public UserRequests userReq;
    public Responses userResponse;
   // public UserRequests userReq;

    //create services here so you dont create multiple dAO obj

    public Handlers(){
       userResponse = new Responses();
    }
    public Object clearHandler(Request request, Response response, UserService userService,GameService gameService) throws DataAccessException {
        userReq = new UserRequests();

        userReq.error = 200;
        userService.clear();
        gameService.clear();


        // create registerResponse and assign to value
        //then send to serivce
        response.status(userReq.error);
        //return gson.toJson();//put what service returns in here
        //return "check";
        return "{}";
    }
    public Object registerHandler(Request request, Response response, UserService userService) throws DataAccessException {
        userReq = gson.fromJson(request.body(), UserRequests.class);
        userReq.error = 200;

        if (userReq.getUser()== null || userReq.getPassword() == null || userReq.getEmail() == null) {//check if request has valid fields
            // 400 error bad request
            userReq.error = 400;
            response.status(userReq.error);
            return gson.toJson(userResponse.message(new ErrorMessage("error:bad request")));
        }
        var tmp = userService.registerUser(userReq);
        userResponse = new Responses();
        // create registerResponse and assign to value
        //then send to serivce
        response.status(userReq.error);
        if(tmp.getAuthToken() == null){
            return gson.toJson(userResponse.message(new ErrorMessage(tmp.getUsername())));
        }
        //return gson.toJson();//put what service returns in here
        //return message if error code
        return gson.toJson(userResponse.registerResponse(tmp));
    }

    public Object loginHandler(Request request, Response response, UserService userService)throws DataAccessException{
        var userReq = gson.fromJson(request.body(), UserRequests.class);

        AuthData authdata = userService.login(userReq);
        response.status(userReq.error);
        if(authdata == null){
            return gson.toJson(new ErrorMessage("error"));
        }
        return gson.toJson(userResponse.loginResponse(authdata));
    }
    public Object logoutHandler(Request request, Response response,UserService userService) throws DataAccessException{
        var userReq = new UserRequests();
        var at = request.headers("authorization");
        userReq.setAuthtoken(at);
        userService.logout(userReq);
        if(userReq.error != 200){
            response.status(userReq.error);
            return gson.toJson(new ErrorMessage("error"));
        }
        response.status(userReq.error);
        return "{}";
    }

    public Object listGameHandler(Request request, Response response,UserService userService, GameService gameService) throws DataAccessException{
        var userReq = new UserRequests();
        var at = request.headers("authorization");
        userReq.setAuthtoken(at);

        if(userService.verifyAuth(at) == null) {
            userReq.error = 401;//error 401 unauthorized
            response.status(userReq.error);
            return gson.toJson(new ErrorMessage("error: unauthorized"));
        }else{
            userReq.error = 200;
        }

        var listOfGames = gameService.listGames(userReq);
        response.status(userReq.error);
        if(listOfGames == null){
            return gson.toJson( new ErrorMessage("error"));
        }
        return gson.toJson(Map.of("games",userResponse.listGameResponse(listOfGames)));
    }

    public Object newGameHandler(Request request, Response response,UserService userService, GameService gameService) throws DataAccessException{
        var userReq = gson.fromJson(request.body(), UserRequests.class);
        var at = request.headers("authorization");
        userReq.setAuthtoken(at);

        if(userService.verifyAuth(at) == null) {
            userReq.error = 401;//error 401 unauthorized
            response.status(userReq.error);
            return gson.toJson(new ErrorMessage("error: unauthorized1"));

        }else{
            userReq.error = 200;
        }

        var game = gameService.newGame(userReq);

        response.status(userReq.error);
        if(game == null){
            return gson.toJson(new ErrorMessage("error"));
        }
        return gson.toJson(Responses.newGameResponse(game));
    }
    public Object joinGameHandler(Request request, Response response,UserService userService, GameService gameService) throws DataAccessException{
        var userReq = gson.fromJson(request.body(), UserRequests.class);
        var at = request.headers("authorization");
        userReq.setAuthtoken(at);
        var authdata = userService.verifyAuth(at);
        if( authdata == null) {
            userReq.error = 401;//error 401 unauthorized
            response.status(userReq.error);
            return gson.toJson(new ErrorMessage("error: unauthorized1"));

        }else{
            userReq.error = 200;

            userReq.setUsername(authdata.getUsername());
            gameService.joinGame(userReq);
        }

         response.status(userReq.error);
        if(userReq.error != 200){
            return gson.toJson(new ErrorMessage("error"));
        }
         return "{}";
    }
}
