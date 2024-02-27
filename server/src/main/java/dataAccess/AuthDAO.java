package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class AuthDAO {
    public static Map<String, AuthData> token = new HashMap<>();

    //find authorization
    public AuthData findAuth(String user) throws DataAccessException {
        return token.get(user);//returns Authtoken variable/data
    }
    public AuthData findUser(String auth){
        for(Map.Entry<String, AuthData> value : token.entrySet()){
            if(value.getValue().getAuthToken().equals(auth)){
                return value.getValue();
            }
        }
        return null;
    }



    public void delete(String user) {
        token.remove(user);
        // remove key or replace the authorization?
    }

    public void clear() {
        //deletes all instances
        token.clear();

    }

    //delete, insert, clear
    public void insert(AuthData t) {
        //inserts the reandom string and username into map
        token.put(t.getUsername(), t);

    }

    public AuthData setAuthtoken(AuthData auth) {

        return token.replace(auth.getUsername(), auth);
    }
}
