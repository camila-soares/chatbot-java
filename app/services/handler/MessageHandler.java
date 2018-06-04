package services.handler;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;

import com.fasterxml.jackson.databind.JsonNode;
import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.assistant.v1.model.OutputData;
import com.typesafe.config.Config;

import models.chatsdk.Message;
import models.chatsdk.Payload;
import modules.UserContext;

import services.WatsonService;

import javax.inject.Inject;
import java.util.List;



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
    public void onReceive(Object message)  {
        if(message instanceof UserContext){
            handleWatsonMessage((UserContext) message);
        }else if(message instanceof JsonNode){
            handlerUserMessage((Message) message);
        }
    }

    private void handleWatsonMessage(UserContext context) {
        MessageResponse response = context.getWatsonResponse();
        Context watsonContext = context.getContext ();
        boolean skipUserInput = Boolean.parseBoolean ( watsonContext.getOrDefault ( "skip_user_input", false ).toString () );
        if(skipUserInput)  watsonService.checkClientAction ( context, getSender () );

        OutputData outputData = response.getOutput ();
        List<String> msg = outputData.getText ();
        msg.forEach ( text -> {
            Message message = new Message (  );
            message.setAction(Message.Action.TEXT);
            Payload payload = new Payload ();
            payload.setText ( text );
            message.setPayload(payload);
            sendMessageToUser ( getSender (), context, null, message );
        } );

        }

    private void sendMessageToUser(ActorRef sender , UserContext context , String messageClient , Message message) {
        sender.tell ( messageClient, self () );
        context.getWatsonResponse ();
    }



    private void noCommandHandler(UserContext context , String text) {
        Message message = new Message (  );
        message.setAction(Message.Action.TEXT);

        Payload payload = new Payload ();
        payload.setText ( text );
        message.setPayload ( payload );

        sendMessageToUser(sender(), context, text, message);
    }


    private void handlerUserMessage(Message mesaage) {
       Payload userPayload = mesaage.getPayload ();
       if(userPayload != null) {
           switch (mesaage.getAction ()) {
               case POSTBACK:
                   break;
               case TEXT:
               default:
                   watsonService.sendMessageToWatson ( userPayload.getText () , context , null , null , false , getSender () );
           }
       }else
           watsonService.sendHi ( context, getSender () );

       }




    }

