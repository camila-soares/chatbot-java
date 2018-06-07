package services.handler;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import com.fasterxml.jackson.databind.JsonNode;
import models.chatsdk.Message;




public class WebsocketHandler extends UntypedAbstractActor {
    private final String LOG_TAG = "[" + this.getClass ().getSimpleName () + "]";

    private final ActorRef out;
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
            String msg = (( JsonNode ) obj).path ( "message" ).textValue ();
            Message message = new Message ( msg );
            messageHandler.tell ( message , getSelf () );
        }
    }
}
