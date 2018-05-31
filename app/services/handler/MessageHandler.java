package services.handler;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedAbstractActor;

import com.fasterxml.jackson.databind.JsonNode;
import com.ibm.watson.developer_cloud.conversation.v1.model.Context;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.typesafe.config.Config;
import models.chatsdk.Message;
import modules.UserContext;
import play.libs.Json;
import services.WatsonService;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class MessageHandler extends UntypedAbstractActor {

    private final WatsonService watsonService;
    private UserContext context;
    private final Config config;

    @Inject
    public MessageHandler(WatsonService watsonService, Config config) {
        this.watsonService = watsonService;
        this.config = config;

    }


    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof UserContext){
            handleWatsonMessage((UserContext) message);
        }else if(message instanceof JsonNode){
            handlerUserMessage((JsonNode) message);
        }

    }

    private void handleWatsonMessage(UserContext context) {
        MessageResponse response = context.getWatsonResponse();
        List<String> textList = response.getOutput().getText();
        final String text = textList.size() != 0 ? textList.get(0): "";
        Context watsonContext = context.getContext();
        ActorRef sender = sender();

        sendMessageToUser( sender, context, text);

        noCommandHandler(context, text);

        context.setContext(watsonContext);

        }

    private void sendMessageToUser(ActorRef sender , UserContext context , String text) {
        sender.tell ( text, self () );
    }



    private void noCommandHandler(UserContext context , String text) {
        sendMessageToUser(sender(), context, text);
    }


    private void handlerUserMessage(JsonNode messageJson) {
        Message userInput = Json.fromJson(messageJson, Message.class);

        watsonService.sendMessageToWatson(userInput.getTexto (), context, null, null, false, getSender());


    }
}
