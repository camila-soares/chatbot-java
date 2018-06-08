package models.chatsdk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import modules.UserContext;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

    public enum Action {
        TEXT,

    }

    @JsonIgnore
    private Action action;
   private String text;

    public Message() {
    }

    public Message(String text) {
        this.action = Action.TEXT;
        this.text = text;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
