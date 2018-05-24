package controllers;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import com.google.inject.name.Named;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.WebSocket;
import services.AuthenticationService;
import services.CacheApi;
import services.handler.WebsocketHandler;

import javax.inject.Inject;


public class BotController extends Controller {

    private final ActorSystem actorSystem;
    private final Materializer materializer;
    private final ActorRef messageHandler;
    private final ActorRef authService;



    @Inject
    public BotController(ActorSystem actorSystem,
                         Materializer materializer,
                         AuthenticationService authService,
                         CacheApi cacheApi,
                         @Named("message-handler") ActorRef messageHandler) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
        this.messageHandler = messageHandler;
        this.authService = authService;

    }

    public WebSocket socket() {
        String token = request ().getQueryString ( "" );
        return WebSocket.Json.accept(request ->
                ActorFlow.actorRef(actorRef -> WebsocketHandler.props(actorRef,actorSystem, messageHandler,authService, token),actorSystem, materializer));
    }
}
