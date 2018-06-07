package modules;


import com.ibm.watson.developer_cloud.conversation.v1.model.Context;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import java.io.Serializable;

public class UserContext implements Serializable {


    private Context context;
    private transient MessageResponse watsonResponse;

    public UserContext() {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MessageResponse getWatsonResponse() {
        return watsonResponse;
    }

    public void setWatsonResponse(MessageResponse watsonResponse) {
        this.watsonResponse = watsonResponse;
        this.context = watsonResponse.getContext();
    }
}