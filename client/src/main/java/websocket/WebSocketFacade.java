package websocket;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class WebSocketFacade {
    Session session;
    NotificationHandler notificationHandler;
    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws DataAccessException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(new MessageHandler.Whole<String>(){
                @Override
                public void onMessage (String message){
                    ServerMessage notification = new Gson().fromJson(message,ServerMessage.class);
                    notificationHandler.notify(notification);
                }
            });

        }catch(DeploymentException | IOException | URISyntaxException ex){
            throw new DataAccessException("500");
        }
        //endpoints


    }
}
