package models.chatsdk;

public class Payload {

    public class Postback {
        public static final String ABOUT_MOVIE = "about_movie_";
        public static final String ABOUT_HIGHLIGHT = "about_highlight_";
        public static final String ABOUT_SERVICE = "about_service_";
        public static final String ABOUT_STORE = "about_store_";
        public static final String LIST_MOVIES = "view_movies";
        public static final String LIST_HIGHLIGHTS = "view_highlights";
        public static final String LIST_STORES = "view_stores_";
        public static final String LIST_SERVICES = "view_services";
        public static final String TALK_TO_ATTENDANT = "talk_to_attendant";
        public static final String HOW_TO_GET_TO_THE_MALL = "how_to_get_to_the_mall";
        public static final String SCHEDULE = "schedule";
        public static final String GET_STARTED = "5y9iJFVlkrWt9Ke5R7meOxiB8d7r";
    }

    public class Navigate {
        public static final String MOVIES = "/cinema/";
        public static final String HIGHLIGHTS = "/destaques";
    }

    private String text;
    private String postback;
    private String quickreply;

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

    public String getQuickreply() {
        return quickreply;
    }

    public void setQuickreply(String quickreply) {
        this.quickreply = quickreply;
    }
}
