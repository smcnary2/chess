package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UsersDAO;
import model.AuthData;
import model.User;
import model.UserRequests;

import java.util.UUID;

public class UserService {
    UsersDAO pushToUserDAO;
    AuthDAO pushToAuthDAO;

    public UserService(){
        pushToAuthDAO = new AuthDAO();
        pushToUserDAO = new UsersDAO();
    }

    public AuthData registerUser(UserRequests newrequest)throws DataAccessException{
        if (newrequest.error == 200) {
            User newUser = new User(newrequest.getUser(), newrequest.getPassword(), newrequest.getEmail());
            pushToUserDAO.insertUser(newUser);
            AuthData t = new AuthData(UUID.randomUUID().toString(), newrequest.getUser());//creates a unique string for authtokin
            pushToAuthDAO.insert(t);//is this where I'm supposed to create the authtoken
            return pushToAuthDAO.findAuth(t.getAuthToken());
        }

        return null;
    }
    public void clear(UserRequests r) throws DataAccessException{
        pushToAuthDAO.clear();
        pushToUserDAO.clearAllUsers();
    }
}
