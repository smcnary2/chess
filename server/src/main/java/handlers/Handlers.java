package handlers;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class Handlers {
    private Gson gson = new Gson();
    private UsersDAO data;

    public Handlers(){
       data = new UsersDAO;
    }

    public Object clearHandler(Request request, Response response) {

        return 0;
    }
    public Object registerHandler(Request request, Response response){
        return 0;
    }
}
