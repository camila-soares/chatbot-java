package services;


import akka.actor.ActorRef;
import com.google.inject.name.Named;
import com.ibm.watson.developer_cloud.conversation.v1.model.*;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.typesafe.config.Config;
import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import modules.UserContext;
import play.Logger;
import play.api.Play;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class WatsonService {
    private final String LOG_TAG = this.getClass().getSimpleName();

    private Conversation conversation;
    private final String workspaceId;
    private ActorRef handlerActor;

    @Inject
    public WatsonService(Conversation conversation,
                         Config config,
                         @Named("user-context") ActorRef handlerActor) {
        this.conversation = conversation;
        this.handlerActor = handlerActor;

        this.workspaceId = config.getString("conversation.api.workspaceId");
    }

    public void sendMessageToWatson(String inputText,
                                    UserContext context,
                                    List<RuntimeIntent> intents,
                                    List<RuntimeEntity> entities,
                                    boolean alternateIntents,
                                    ActorRef sender) {

        if (context == null)
            throw new IllegalArgumentException();

        MessageOptions.Builder messageBuilder = new MessageOptions.Builder();
        messageBuilder.workspaceId(workspaceId);

        InputData.Builder inputBuilder = new InputData.Builder();
        inputBuilder.text(inputText);

        messageBuilder.input(inputBuilder.build());
       // messageBuilder.alternateIntents(alternateIntents);


        if (context.getWatsonContext() != null) messageBuilder.context(context.getContext());
        if (intents != null && !intents.isEmpty()) messageBuilder.intents(intents);
        if (entities != null) messageBuilder.entities(entities);

        conversation.message(messageBuilder.build()).enqueue(new ServiceCallback<MessageResponse>() {

            @Override
            public void onResponse(MessageResponse response) {
                context.setWatsonResponse(response);
                context.setContext(response.getContext());
                handlerActor.tell(context, sender);

            }

            @Override
            public void onFailure(Exception e) {
                Logger.error("[{}] {}", LOG_TAG, e.getMessage());
                if (Play.current().isDev())
                    e.printStackTrace();
            }
        });

    }
    }



