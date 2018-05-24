package services.handler;

import akka.actor.*;
import models.chatsdk.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import modules.UserContext;
import play.Logger;
import play.libs.Json;
import services.AuthenticationService;

public class WebsocketHandler extends UntypedAbstractActor {

    private final String LOG_TAG = "[" + this.getClass().getSimpleName() + "]";
    private final ActorRef messageHandler;
    private final String secret;
    private final AuthenticationService authService;
    private ActorSystem actorSystem;
    private final ActorRef out;


    public static Props props(ActorRef out, ActorSystem actorSystem, ActorRef authService, ActorRef messageHandler, String token) {
        return Props.create(WebsocketHandler.class, out, actorSystem, authService, messageHandler, token);
    }

    public WebsocketHandler(ActorRef out, ActorSystem actorSystem, ActorRef messageHandler,  AuthenticationService authService, String token) {

        this.out = out;
        this.actorSystem = actorSystem;
        this.messageHandler = messageHandler;
        this.secret = token;
        this.authService = authService;

    }

    public void onReceive(Object obj) throws Exception {
        if (obj instanceof JsonNode) {
            // Handling incoming messages
            Message message = Json.fromJson((JsonNode) obj, Message.class);
            switch (message.getAction()) {
                case HELLO:
                    checkUserContext((ObjectNode) obj);
                    break;
                case PING:
                    handlePing();
                    break;
                default:
                    messageHandler.tell(obj, getSelf());
            }
        } else if (obj instanceof UserContext) {
            messageHandler.tell(obj, getSelf());
        } else if (obj instanceof Message) {
            // Handling output messages
            out.tell(Json.toJson(obj), getSelf());
        } else
            unhandled(obj);
    }

    private void checkUserContext(ObjectNode message) {
        try {
            String subject;
            boolean isNewToken = message.path("token").isMissingNode();
            if (isNewToken) {
                Claims claims = authService.validateSecret(secret);
                subject = claims.getSubject();
                ObjectNode configNode = Json.newObject();
                configNode.put("action", "CONFIG");
                configNode.put("token", subject);
                out.tell(configNode, getSelf());
                message.put("token", subject);
            } else {
                subject = message.get("token").textValue();
            }

            if (isNewToken)
                messageHandler.tell(message, getSelf());
            Logger.debug("{} Checking user context: {}", LOG_TAG, subject);
        } catch (JwtException e) {
            Logger.error("{} {}", LOG_TAG, e.getMessage());
            actorSystem.stop(out);
        }
    }

    private void handlePing() {
        Message msg = new Message();
        msg.setAction(Message.Action.PONG);
        out.tell(Json.toJson(msg), getSelf());
    }
}