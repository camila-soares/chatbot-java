package services.handler;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.handler.codec.MessageAggregationException;
import models.chatsdk.Message;
import modules.UserContext;
import play.Logger;
import play.libs.Json;


public class WebsocketHandler extends UntypedAbstractActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final ActorRef out;
    private final ActorRef messageHandler;
    private ActorSystem actorSystem;

    public static Props props(ActorRef out, ActorSystem actorSystem, ActorRef messageHandler) {
        return Props.create(WebsocketHandler.class , out, actorSystem, messageHandler);
    }

    public WebsocketHandler(ActorRef out , ActorSystem actorSystem, ActorRef messageHandler) {
        this.out = out;
        this.actorSystem = actorSystem;
        this.messageHandler = messageHandler;
    }

    public void onReceive(Object obj) throws Exception {
        if(obj instanceof JsonNode){
            log.info("Received Json Message:{}", obj );
            getSender().tell(obj,getSelf());
            checkUserContext((ObjectNode) obj);
        }
    }

    private void checkUserContext(ObjectNode msg) {
       ObjectNode configNode = Json.newObject ();
       out.tell ( configNode, getSelf ());
    }

}

