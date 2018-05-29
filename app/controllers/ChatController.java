package controllers;

import play.mvc.Controller;
import play.mvc.Result;


public class ChatController extends Controller {
    public Result chat() {

      return ok(views.html.chat.render ());
    }

    private Integer getIdUrl(String url) {
        if(url.contains("localhost:9000/ws"))
            return 1;
        return null;
    }




}




