package dataAccess;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    List<User> databasePlaceholder;

    public UsersDAO() {
        databasePlaceholder = new ArrayList<>();
    }
    public void insertUser(User newUser) throws DataAccessException {// insert user
        databasePlaceholder.add(newUser);
        //System.out.println(newUser.getUsername());
        //System.out.print(databasePlaceholder.get(0).getUsername() + databasePlaceholder.get(0).getPassword() + databasePlaceholder.get(0).getEmail());
    }
    public void clearAllUsers() {
        for (User i :
                databasePlaceholder) {
            databasePlaceholder.remove(i);
        }

    }
    public boolean databaseIsEmpty() {
        return databasePlaceholder.isEmpty();
    }
    public List<User> findAllUsers() throws DataAccessException {
        return databasePlaceholder;//is this safe
    }
}
