package modules;


import akka.routing.RoundRobinPool;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;
import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.typesafe.config.Config;
import play.Environment;
import play.libs.Json;
import play.libs.akka.AkkaGuiceSupport;
import services.handler.MessageHandler;

import java.time.ZoneOffset;
import java.util.TimeZone;


public class Application extends AbstractModule implements AkkaGuiceSupport {
    private final Config config;

    public Application(Environment environment, Config config){this.config = config;}

    @Override
    protected void configure() {

        Conversation conversation = new Conversation(
                config.getString("conversation.api.version"),
                config.getString("conversation.api.username"),
                config.getString("conversation.api.password")
        );
        bind(Conversation.class).toInstance(conversation);

        bindActor(MessageHandler.class, "user-context", props -> props.withRouter(new RoundRobinPool(5)));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.registerModule(new JavaTimeModule());
        mapper.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Json.setObjectMapper(mapper);

        bindActor(MessageHandler.class, "message-handler");
    }



}
