package services.handler;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import com.fasterxml.jackson.databind.JsonNode;
import models.chatsdk.Message;
import modules.UserContext;
import play.libs.Json;


public class WebsocketHandler extends UntypedAbstractActor {

    private final ActorRef out;

    public static Props props(ActorRef out) {
        return Props.create ( WebsocketHandler.class , out );
    }

    public WebsocketHandler(ActorRef out) {
        this.out = out;
    }


    public void onReceive(Object obj) {
        if(obj instanceof JsonNode){
           Message message = Json.fromJson ( (JsonNode) obj, Message.class );
          message.equals ( obj );
          out.tell ( obj, getSelf () );
        }else if(obj instanceof UserContext){
            out.tell(obj, getSelf ());
        }else if (obj instanceof Message){
            out.tell (Json.toJson (obj), getSelf ());
        }else
            unhandled (obj);

    }

}

