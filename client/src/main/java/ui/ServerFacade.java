package ui;

import com.google.gson.Gson;
import model.AuthData;
import model.User;
import model.UserRequests;
import model.WebGame;
import server.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
    private final String serverUrl;
    public Server server;
    public ServerFacade(String url){

//        server = new Server();
//        server.run(8080);
        serverUrl = url;
    }

    public AuthData register(User user){
       var path = "/user";
       return this.makeRequest("POST", path, user, AuthData.class);
    }
    public Object logout(AuthData authdata){
        var path = String.format("/session/%s", authdata.getAuthToken());
        return this.makeRequest("DELETE", path, authdata, null);
    }

    public AuthData login(User user){
        var path = "/session";
        return this.makeRequest("POST", path, user, AuthData.class);
    }
    public WebGame creategame(String auth, WebGame newgame){
        var path = String.format("/game/%s", auth);
        return this.makeRequest("POST",path, newgame, WebGame.class);
    }
    public WebGame[] listgames(String auth){
        var path = String.format("/game/%s", auth);
        record listgameResponse(WebGame[] game){
        }
        var response = this.makeRequest("GET", path, null, listgameResponse.class );

        return response.game();
    }
    public void joinGame(String auth, UserRequests game){
        var path = String.format("/game/%s",auth);
        this.makeRequest("PUT", path, game, null);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass){
        try{
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            writeBody(request, http);
            http.connect();

            return readBody(http, responseClass);

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;//probably will have to change
    }
    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {

            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }
}
