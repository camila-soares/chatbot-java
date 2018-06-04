package models.chatsdk;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload {

    private String text;
    private String postback;
    private List<String> quickreply;
    private List<Menu> menu;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostback() {
        return postback;
    }

    public void setPostback(String postback) {
        this.postback = postback;
    }

    public List<String> getQuickreply() {
        return quickreply;
    }

    public void setQuickreply(List<String> quickreply) {
        this.quickreply = quickreply;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    private class Menu {
        private String title;
        private String postback;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPostback() {
            return postback;
        }

        public void setPostback(String postback) {
            this.postback = postback;
        }
    }
}
