package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.*;
import model.AuthData;
import model.Responses;
import model.UserRequests;
import service.UserService;
import spark.Request;
import spark.Response;

public class Handlers {
    private Gson gson = new Gson();
    public UsersDAO data;
    public Responses userResponse;
   // public UserRequests userReq;

    //create services here so you dont create multiple dAO obj

    public Handlers(){
       data = new UsersDAO();
    }
    public Object clearHandler(Request request, Response response, UserService userService) throws DataAccessException {
        UserRequests r = gson.fromJson(request.body(), UserRequests.class);
        r = new UserRequests();
        r.error = 200;


        userService.clear(r);
        if (!(data.databaseIsEmpty())) {
            r.error = 500;
        }
        // create registerResponse and assign to value
        //then send to serivce
        response.status(200);
        //return gson.toJson();//put what service returns in here
        userResponse = new Responses();
        //return "check";
        return gson.toJson(userResponse.returnMessage(r.error));
    }
    public Object registerHandler(Request request, Response response, UserService userService) throws DataAccessException {
        var userReq = gson.fromJson(request.body(), UserRequests.class);
        userReq.error = 200;

        if (userReq.getUser().isEmpty() || userReq.getPassword().isEmpty() || userReq.getEmail().isEmpty()) {//check if request has valid fields
            // 400 error bad request
            userReq.error = 400;
        }
        var tmp = userService.registerUser(userReq);
        userResponse = new Responses();
        // create registerResponse and assign to value
        //then send to serivce
        response.status(userReq.error);
        //return gson.toJson();//put what service returns in here
        //return message if error code
        //userService.checkList();
        return gson.toJson(userResponse.registerResponse(tmp));
    }

    public Object loginHandler(Request request, Response response, UserService userService)throws DataAccessException{
        var userReq = gson.fromJson(request.body(), UserRequests.class);

        AuthData authdata = userService.login(userReq);
        response.status(userReq.error);
        return gson.toJson(userResponse.loginResponse(authdata));
    }
    public Object logoutHandler(Request request, Response response,UserService userService) throws DataAccessException{
        var userReq = new UserRequests();
        var at = request.headers("authorization");
        userReq.setAuthtoken(at);
        userService.checkList();
        userService.logout(userReq);

        response.status(userReq.error);
        return "{}";
    }
}
