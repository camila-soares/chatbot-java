package services.handler;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import com.fasterxml.jackson.databind.JsonNode;
import jdk.nashorn.internal.ir.ObjectNode;
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


    @Override
    public void onReceive(Object obj) throws Throwable {
        if(obj instanceof JsonNode){
            Message message = Json.fromJson((JsonNode) obj, Message.class);
            switch (message.getAction ()){
                case TEXT:
                    checkUserContext((ObjectNode) obj);
                    break;
                case PING:
                    handlerPing();
                    break;
                    default:
                        out.tell(obj, getSelf ());
            }
        }else if(obj instanceof UserContext){
            out.tell(obj, getSelf ());
        }else if (obj instanceof Message){
            out.tell (Json.toJson (obj), getSelf ());
        }else
            unhandled (obj);

    }

    private void checkUserContext(ObjectNode obj) {

    }

    private void handlerPing() {
        Message msg = new Message ();
        msg.setAction (Message.Action.PONG);
        out.tell(Json.toJson(msg), getSelf());
    }

}

