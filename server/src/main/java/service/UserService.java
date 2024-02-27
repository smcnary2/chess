package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UsersDAO;
import model.AuthData;
import model.User;
import model.UserRequests;

import java.util.UUID;

public class UserService {
    public UsersDAO pushToUserDAO;
    public AuthDAO pushToAuthDAO;

    public UserService(){
        pushToAuthDAO = new AuthDAO();
        pushToUserDAO = new UsersDAO();
    }

    public AuthData registerUser(UserRequests newrequest)throws DataAccessException{
        for (User x :
                pushToUserDAO.findAllUsers()) {
            if (x.getUsername().equals(newrequest.getUser())) {
                //403 error already taken
                newrequest.error = 403;
            }
            if (x.getEmail().equals(newrequest.getEmail())) {
                //403 error already taken
                newrequest.error = 403;
            }
        }
        if (newrequest.error == 200) {
            User newUser = new User(newrequest.getUser(), newrequest.getPassword(), newrequest.getEmail());
            pushToUserDAO.insertUser(newUser);
            //System.out.println(pushToUserDAO.findUser(newUser));
            AuthData t = new AuthData(UUID.randomUUID().toString(), newrequest.getUser());//creates a unique string for authtokin
            pushToAuthDAO.insert(t);//is this where I'm supposed to create the authtoken
            return pushToAuthDAO.findAuth(t.getUsername());
        }

        return null;
    }

    public void clear(UserRequests r) throws DataAccessException{
        pushToAuthDAO.clear();
        pushToUserDAO.clearAllUsers();
    }
    public AuthData login(UserRequests newrequest) throws DataAccessException {

        if (pushToAuthDAO.findAuth(newrequest.getUser()) != null) {
            newrequest.error = 401; //401 error unauthorized
            return new AuthData("already logged in", "unauthorized");
        }

        User newUser = new User(newrequest.getUser(), newrequest.getPassword());

        if (pushToUserDAO.findUser(newUser) == null) {
            newrequest.error = 500;
            return new AuthData("cannot find User", "invalid field");
        }
        newrequest.error = 200;
        AuthData t = new AuthData(UUID.randomUUID().toString(), newrequest.getUser());
        pushToAuthDAO.insert(t);
        //return response
        return pushToAuthDAO.findAuth(newrequest.getUser());
    }

    public void logout(UserRequests r) throws DataAccessException {
        var authdata = pushToAuthDAO.findUser(r.authtoken);
        if (authdata == null) {
            r.error = 401;//error 401 unauthorized
        } else {
            r.error = 200;
            pushToAuthDAO.delete(authdata.getUsername());
        }

    }
}
