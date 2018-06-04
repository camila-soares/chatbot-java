package services;


import akka.actor.ActorRef;

import com.google.gson.internal.LinkedTreeMap;
import com.google.inject.name.Named;
import com.ibm.watson.developer_cloud.assistant.v1.Assistant;


import com.ibm.watson.developer_cloud.assistant.v1.model.*;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.typesafe.config.Config;
import modules.UserContext;
import play.Logger;
import play.api.Play;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


@Singleton
public class WatsonService {
    private final String LOG_TAG = this.getClass ().getSimpleName ();

    private Assistant assistant;
    private final String workspaceId;
    private ActorRef handlerActor;

    @Inject
    public WatsonService(Assistant assistant , Config config , @Named("user-context") ActorRef handlerActor) {
        this.assistant = assistant;
        this.handlerActor = handlerActor;

        this.workspaceId = config.getString ( "assistant.api.workspaceId" );
    }

    public void sendMessageToWatson(String inputText , UserContext context , List <RuntimeIntent> intents , List <RuntimeEntity> entities , boolean alternateIntents , ActorRef sender) {

        if (context == null) throw new IllegalArgumentException ();


        MessageOptions.Builder messageBuilder = new MessageOptions.Builder ();
        messageBuilder.workspaceId ( workspaceId );

        InputData.Builder inputBuilder = new InputData.Builder ();
        inputBuilder.text ( inputText );

        messageBuilder.input ( inputBuilder.build () );
        messageBuilder.alternateIntents ( alternateIntents );


        if (context.getContext() != null) messageBuilder.context(context.getContext());
        if (intents != null && !intents.isEmpty ()) messageBuilder.intents ( intents );
        if (entities != null) messageBuilder.entities ( entities );

        sendMessage ( context , sender , messageBuilder );

    }

    public void sendClientResponse(UserContext context , ActorRef sender) {
        MessageResponse response = context.getWatsonResponse ();
        MessageOptions.Builder messageBuilder = new MessageOptions.Builder ();
        messageBuilder.workspaceId ( workspaceId )
                      .input ( new InputData.Builder ().text ( response.getInput ().getText () ).build () )
                      .intents ( response.getIntents () )
                      .entities ( response.getEntities () )
                      .context ( context.getContext () );
        sendMessage ( context , sender , messageBuilder );
    }


    private void sendMessage(UserContext context , ActorRef sender , MessageOptions.Builder messageBuilder) {
        assistant.message ( messageBuilder.build () ).enqueue(new ServiceCallback<MessageResponse> () {
            @Override
            public void onResponse(MessageResponse response) {
                context.setWatsonResponse ( response );
                context.setContext ( response.getContext () );
                handlerActor.tell ( context , sender );
            }

            @Override
            public void onFailure(Exception e) {
                Logger.error ( "[{}] {}" , LOG_TAG , e.getMessage () );
                if (Play.current ().isDev ()) e.printStackTrace ();
            }


        } );
    }

    @SuppressWarnings("unchecked")
    public void checkClientAction(UserContext context, ActorRef sender) {
        List<LinkedTreeMap> actions = (List<LinkedTreeMap>) context.getWatsonResponse().get("actions");
        actions.forEach(action -> {
            String name = action.getOrDefault("name", "").toString();
            String type = action.getOrDefault("type", "").toString();
            String resultVariable = action.getOrDefault("result_variable", "").toString();
            if (type.equals("client")) {
                LinkedTreeMap parameters = (LinkedTreeMap) action.get("parameters");

                Optional<Method> methodOptional = Arrays.stream(getClass().getDeclaredMethods()).filter(method -> method.getName().equals(name)).findFirst();
                methodOptional.ifPresent(method -> {
                    try {
                        Set<String> keySet = parameters.keySet();
                        Object[] params = new Object[keySet.size()];
                        int i = 0;
                        for (String key : keySet) {
                            params[i] = parameters.get(key);
                            i++;
                        }
                        Object result = method.invoke(this, params);
                        context.getContext().put(resultVariable, result);
                        sendClientResponse(context, sender);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    public void sendHi(UserContext context, ActorRef sender){
        MessageOptions.Builder builder = new MessageOptions.Builder (  );
        builder.workspaceId ( workspaceId );
        sendMessage ( context, sender, builder );
    }

    }





