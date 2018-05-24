package models.chatsdk;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    public enum Action {
        CONFIG,
        HELLO,
        PING,
        PONG,
        TEXT,
        IMAGE,
        BUBBLE,
        CAROUSEL,
        POSTBACK,
        QUICKREPLY,
        STORES
    }

    private Action action;
    private Payload payload;
    private List<Bubble> bubbles;

    public Message() {
    }

    public Message(String text) {
        this.action = Action.TEXT;
        this.payload = new Payload();
        this.payload.setText(text);
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public List<Bubble> getBubbles() {
        return bubbles;
    }

    public void setBubbles(List<Bubble> bubbles) {
        this.bubbles = bubbles;
    }

    public void addBubble(Bubble bubble) {
        if (this.bubbles == null)
            this.bubbles = new ArrayList<>();
        this.bubbles.add(bubble);
    }
}
