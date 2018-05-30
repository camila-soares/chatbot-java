package services;


import akka.actor.ActorRef;
import com.google.inject.name.Named;
import com.ibm.watson.developer_cloud.conversation.v1.model.*;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.typesafe.config.Config;
import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import modules.UserContext;
import play.Logger;
import play.api.Play;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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
        messageBuilder.alternateIntents(alternateIntents);


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
    
    public CompletionStage<Void> createEntityValue(String entity, Map<String, List<String>> values, boolean fuzzyMatched) {
        CompletableFuture<Void> aVoid = new CompletableFuture <> ();
        GetEntityOptions.Builder getBuilder = new GetEntityOptions.Builder ( );
        getBuilder.workspaceId ( workspaceId );
        getBuilder.entity (  entity);
        getBuilder.export ( true );
        ServiceCall<EntityExport> getServiceCall = conversation.getEntity ( getBuilder.build () );
        getServiceCall.enqueue ( new ServiceCallback<EntityExport>() {
            @Override
            public void onResponse(EntityExport response) {
                Logger.debug("[{}] Updating existing entity", LOG_TAG);

                if(entity.equals(response.getEntityName())){
                    UpdateEntityOptions.Builder builder = new UpdateEntityOptions.Builder();
                    builder.entity(entity);
                    builder.newFuzzyMatch(fuzzyMatched);
                    builder.newValues(getUpdateValues(response.getValues(), values));
                    builder.workspaceId ( workspaceId );
                    ServiceCall<Entity> serviceCall = conversation.updateEntity ( builder.build () );
                    serviceCall.enqueue ( new ServiceCallback <Entity> () {
                        @Override
                        public void onResponse(Entity response) {
                            aVoid.complete ( null );
                            Logger.info ( "[{}] {] ", LOG_TAG, "Entity" + response.getEntityName () + "updated");
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Logger.error ( "[{}] {] ", LOG_TAG, e.getStackTrace ());
                            aVoid.completeExceptionally ( e );
                        }
                    } );
                }
            }

            @Override
            public void onFailure(Exception e) {
                Logger.error("[{}] {}", LOG_TAG, e.getMessage());
                e.printStackTrace();
                aVoid.completeExceptionally(e);
            }
        });
        return aVoid;
    }

    private List<CreateValue> getUpdateValues(List<ValueExport> oldValues, Map<String, List<String>> newValues) {
        List<CreateValue> updateList = new ArrayList <> ( );
        newValues.forEach ((value, synonyms) -> {
            Optional<ValueExport> optValue = oldValues.stream().filter ( ver -> ver.getValueText().equalsIgnoreCase(value)).findFirst ();
            CreateValue.Builder builder = new CreateValue.Builder (  );
            builder.value (value);
            if(optValue.isPresent ()) {
                ValueExport ver = optValue.get ();
                List<String> updatedSynonyms = ver.getSynonyms ();
                synonyms.forEach(s -> {
                    if(!updatedSynonyms.contains ( s ))
                        updatedSynonyms.add ( s );
                });
                builder.synonyms ( updatedSynonyms );
                builder.metadata ( ver.getMetadata () );
            }else {
                builder.synonyms ( synonyms );
            }
            updateList.add ( builder.build () );
        });
        return updateList;
    }
}



