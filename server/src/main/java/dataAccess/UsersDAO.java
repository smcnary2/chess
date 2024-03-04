package dataAccess;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    public List<User> databasePlaceholder;

    public UsersDAO() {
        databasePlaceholder= new ArrayList<>();
    }
    public void insertUser(User newUser) throws DataAccessException {// insert user
        databasePlaceholder.add(newUser);
        //System.out.println(newUser.getUsername());
        //System.out.print(databasePlaceholder.get(0).getUsername() + databasePlaceholder.get(0).getPassword() + databasePlaceholder.get(0).getEmail());
    }
    public void clearAllUsers() {
        databasePlaceholder = new ArrayList<>();

    }
    public List<User> findAllUsers() throws DataAccessException {
        return databasePlaceholder;//is this safe
    }

    public User findUser(User newUser) throws DataAccessException {
        for (User x :
                databasePlaceholder) {
            if (x.getUsername().equals(newUser.getUsername())) {
                if (x.getPassword().equals(newUser.getPassword())) {
                    return x;//what am I supposed to return here
                }
            }
        }
        return null;
    }
}
