package services.handler;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.JwtException;
import models.chatsdk.Message;
import modules.UserContext;
import play.Logger;
import play.libs.Json;


public class WebsocketHandler extends UntypedAbstractActor {
    private final String LOG_TAG = "[" + this.getClass ().getSimpleName () + "]";

    private final ActorRef out;
    private String userId;
    private final ActorRef messageHandler;
    private ActorSystem actorSystem;

    public static Props props(ActorRef out , ActorSystem actorSystem , ActorRef messageHandler) {
        return Props.create ( WebsocketHandler.class , out , actorSystem , messageHandler );
    }

    public WebsocketHandler(ActorRef out , ActorSystem actorSystem , ActorRef messageHandler) {
        this.out = out;
        this.actorSystem = actorSystem;
        this.messageHandler = messageHandler;

    }

    public void onReceive(Object obj) throws Exception {
        if (obj instanceof JsonNode) {
            Message message = Json.fromJson ( ( JsonNode ) obj , Message.class );
            message.setUserId ( "1" );
            messageHandler.tell ( message , getSelf () );
        } else if (obj instanceof Message) {
            out.tell ( Json.toJson ( obj ) , getSelf () );
        } else unhandled ( obj );
    }


    private void setupUserContext() {
        try {
            UserContext userContext = new UserContext ();
            if (userContext == null) {
                userContext = new UserContext ();
                userContext.setUserId ( userId );
            }
            Message message = new Message ();
            message.setUserId ( "1" );
            message.setAction ( Message.Action.HELLO );
            messageHandler.tell ( message , getSelf () );

            Logger.debug ( "{} Checking user context: {}" , LOG_TAG , userId );
        } catch (JwtException e) {
            Logger.error ( "{} {}" , LOG_TAG , e.getMessage () );
        }

    }

    private void handlePing() {
        Message msg = new Message ();
        msg.setAction ( Message.Action.PONG );
        out.tell ( Json.toJson ( msg ) , getSelf () );
    }
}
