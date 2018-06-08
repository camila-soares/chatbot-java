package services;


import akka.actor.ActorRef;

import com.google.inject.name.Named;


import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.*;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.typesafe.config.Config;
import modules.UserContext;
import play.Logger;
import play.api.Play;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;


@Singleton
public class WatsonService {
    private final String LOG_TAG = this.getClass ().getSimpleName ();

    private Conversation conversation;
    private final String workspaceId;
    private ActorRef handlerActor;


    @Inject
    public WatsonService(Conversation conversation,
                         UserContext userContext,
                         Config config,
                         @Named("message-handler") ActorRef handlerActor) {
        this.conversation = conversation;
        this.handlerActor = handlerActor;
        this.workspaceId = config.getString ( "conversation.api.workspaceId" );
    }

    public void sendMessageToWatson(String inputText ,
                                    UserContext userContext ,
                                    List <RuntimeIntent> intents ,
                                    List <RuntimeEntity> entities ,
                                    boolean alternateIntents , ActorRef sender) {

        if (userContext == null)
            throw new IllegalArgumentException ();


        MessageOptions.Builder messageBuilder = new MessageOptions.Builder ();
        messageBuilder.workspaceId ( workspaceId );

        InputData.Builder inputBuilder = new InputData.Builder ();
        inputBuilder.text ( inputText );

        messageBuilder.input(inputBuilder.build());
        messageBuilder.alternateIntents(alternateIntents);


        if (userContext.getContext() != null) messageBuilder.context(userContext.getContext());
        if (intents != null && !intents.isEmpty ()) messageBuilder.intents ( intents );
        if (entities != null) messageBuilder.entities ( entities );

        sendMessage ( userContext , sender , messageBuilder );

    }

    public void sendClientResponse(UserContext userContext , ActorRef sender) {
       MessageResponse response = userContext.getWatsonResponse ();
        MessageOptions.Builder messageBuilder = new MessageOptions.Builder ();
        messageBuilder.workspaceId ( workspaceId )
                      .input( new InputData.Builder ().text ( response.getInput ().getText () ).build () )
                      .intents(response.getIntents())
                      .entities(response.getEntities())
                      .context(userContext.getContext());
        sendMessage (userContext , sender , messageBuilder);
    }


    private void sendMessage(UserContext userContext , ActorRef sender , MessageOptions.Builder messageBuilder) {
        conversation.message(messageBuilder.build()).enqueue(new ServiceCallback<MessageResponse> () {
            @Override
            public void onResponse(MessageResponse response) {
                userContext.setWatsonResponse ( response );
                userContext.setContext ( response.getContext () );
                handlerActor.tell ( userContext , sender );
            }

            @Override
            public void onFailure(Exception e) {
                Logger.error ( "[{}] {}" , LOG_TAG , e.getMessage () );
                if (Play.current ().isDev ())
                    e.printStackTrace ();
            }


        });
    }

    public void sendHi(UserContext context, ActorRef sender){
        MessageOptions.Builder builder = new MessageOptions.Builder (  );
        builder.workspaceId ( workspaceId );
        sendMessage ( context, sender, builder );
    }

}





