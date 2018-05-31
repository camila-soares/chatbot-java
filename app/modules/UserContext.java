package modules;




import com.fasterxml.jackson.databind.JsonNode;
import com.ibm.watson.developer_cloud.conversation.v1.model.Context;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import play.libs.Json;


import java.io.Serializable;

public class UserContext implements Serializable {
    private String watsonContext;
    private transient MessageResponse watsonResponse;

    public UserContext() {
    }


    public String getWatsonContext() {
        return watsonContext;
    }



    public Context getContext() {
        JsonNode contextNode = Json.parse (watsonContext);
        return Json.fromJson(contextNode, Context.class);
    }

    public void setContext(Context context) {
        JsonNode contextNode = Json.toJson(context);
        watsonContext = contextNode.toString();
    }

    public MessageResponse getWatsonResponse() {
        return watsonResponse;
    }

    public void setWatsonResponse(MessageResponse watsonResponse) {
        this.watsonResponse = watsonResponse;
    }
}
