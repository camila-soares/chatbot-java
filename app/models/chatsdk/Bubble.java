package models.chatsdk;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bubble {
    private String title;
    private String subtitle;
    private String imageUrl;
    private String itemUrl;
    private List<Button> buttons;

    public Bubble() {
    }

    public Bubble(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    public void addButton(Button button) {
        if (this.buttons == null)
            this.buttons = new ArrayList<>();
        this.buttons.add(button);
    }

    public static class Button {
        public enum Type {
            POSTBACK,
            URL,
            NAVIGATE
        }

        private Type type;
        private String payload;
        private String label;

        public Button() {
        }

        public Button(Type type, String label, String payload) {
            this.type = type;
            this.label = label;
            this.payload = payload;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
