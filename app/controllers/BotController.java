package controllers;


import akka.actor.ActorSystem;
import akka.stream.Materializer;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.WebSocket;
import services.handler.WebsocketHandler;

import javax.inject.Inject;

import static play.libs.streams.ActorFlow.actorRef;


public class BotController extends Controller {

    private final ActorSystem actorSystem;
    private final Materializer materializer;



    @Inject
    public BotController(ActorSystem actorSystem, Materializer materializer){

        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

    public WebSocket socket() {
        return WebSocket.Json.accept(request ->
                ActorFlow.actorRef(WebsocketHandler::props,
                        actorSystem, materializer
                )
        );
    }
}
