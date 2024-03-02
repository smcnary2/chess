package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class AuthDAO {
    public static Map<String, AuthData> token = new HashMap<>();

    //find authorization
    public AuthData findAuth(String auth) throws DataAccessException {
        var tmp = token.get(auth);//returns Authtoken variable/data
        return tmp;
    }
    public AuthData findUser(String user){
        for(Map.Entry<String, AuthData> value : token.entrySet()){
            if(value.getValue().getUsername().equals(user)){
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
    public void insert(AuthData t) throws DataAccessException {
        //inserts the reandom string and username into map
        token.put(t.getAuthToken(), t);
    }

    public boolean isEmpty(){
        return token.isEmpty();
    }


}
