package controllers;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import com.google.inject.name.Named;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.WebSocket;
import services.handler.WebsocketHandler;

import javax.inject.Inject;



public class BotController extends Controller {

    private  final ActorRef messageHandler;
    private final ActorSystem actorSystem;
    private final Materializer materializer;

    @Inject
    public BotController(ActorSystem actorSystem , Materializer materializer, @Named("message-handler") ActorRef messageHandler){
        this.messageHandler = messageHandler;
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

    public WebSocket socket() {
        return WebSocket.Json.accept(request ->
                ActorFlow.actorRef(actorRef -> WebsocketHandler.props(actorRef, actorSystem, messageHandler), actorSystem, materializer)
        );
    }
}
