package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.UsersDAO;
import model.RegisterRequest;
import model.Responses;
import model.User;
import model.UserRequests;
import service.UserService;
import spark.Request;
import spark.Response;

public class Handlers {
    private Gson gson = new Gson();
    public UsersDAO data;
    public  UserService userService;
    public Responses userResponse;
   // public UserRequests userReq;

    //create services here so you dont create multiple dAO obj

    public Handlers(){
       data = new UsersDAO();
       userService = new UserService();
    }
    public Object clearHandler(Request request, Response response) throws DataAccessException {
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
    public Object registerHandler(Request request, Response response) throws DataAccessException {
        var userReq = gson.fromJson(request.body(), UserRequests.class);
        userReq.error = 200;
        UserService userService = new UserService();
        if (userReq.getUser().isEmpty() || userReq.getPassword().isEmpty() || userReq.getEmail().isEmpty()) {//check if request has valid fields
            // 400 error bad request
            userReq.error = 400;
        }

        for (User x :
                data.findAllUsers()) {
            if (x.getUsername().equals(userReq.getUser())) {
                //403 error already taken
                userReq.error = 403;
            }
            if (x.getEmail().equals(userReq.getEmail())) {
                //403 error already taken
                userReq.error = 403;
            }
       }

        userResponse = new Responses();
        // create registerResponse and assign to value
        //then send to serivce
        response.status(userReq.error);
        //return gson.toJson();//put what service returns in here
        //return message if error code

        return gson.toJson(userResponse.registerResponse(userService.registerUser(userReq)));
    }
}
