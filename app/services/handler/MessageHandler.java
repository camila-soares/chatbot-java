package services.handler;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedAbstractActor;

import com.fasterxml.jackson.databind.JsonNode;
import com.ibm.watson.developer_cloud.conversation.v1.model.Context;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.typesafe.config.Config;
import models.chatsdk.Message;
import models.chatsdk.Payload;
import modules.UserContext;
import play.libs.Json;
import services.WatsonService;

import javax.inject.Inject;
import java.util.List;

public class MessageHandler extends UntypedAbstractActor {

    private final WatsonService watsonService;
    private final ActorSystem akka;
    private UserContext context;

    @Inject
    public MessageHandler(WatsonService watsonService, ActorSystem akka, String googleApikey, Config config) {
        this.watsonService = watsonService;
        this.akka = akka;

    }


    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof UserContext){
            handlerWatsonMessage((UserContext) message);
        }else if(message instanceof JsonNode){
            handlerUserMessage((JsonNode) message);
        }

    }

    private void handlerWatsonMessage(UserContext context) {
        MessageResponse response = context.getWatsonResponse();
        List<String> textList = response.getOutput().getText();
        final String text = textList.size() != 0 ? textList.get(0): "";
        Context watsonContext = context.getContext();
        ActorRef sender = sender();

        String env = String.valueOf(watsonContext.remove("fail"));
        noCommandHandler(context, text);

        context.setContext(watsonContext);

        }

    private void noCommandHandler(UserContext context, String text) {
        Message message = new Message();
        message.setAction(Message.Action.TEXT);

        Payload payload = new Payload();
        payload.setText(text);

        message.setPayload(payload);

        sendMessageToUser(sender(), context, text, message);
    }

    private void sendMessageToUser(ActorRef sender, UserContext context, String text, Message messageForClient) {
        sender.tell(messageForClient, self());

    }


    private void handlerUserMessage(JsonNode messageJson) {
        Message userInput = Json.fromJson(messageJson, Message.class);
        Payload userPay = userInput.getPayload();

        watsonService.sendMessageToWatson(userPay.getText(), context, null, null, false, getSender());
        watsonService.sendMessageToWatson(Payload.Postback.GET_STARTED, context, null, null, false, getSender());


    }
}
