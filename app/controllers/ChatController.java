package controllers;

import play.mvc.Controller;
import play.mvc.Result;


public class ChatController extends Controller {
    public Result chat() {

      return ok(views.html.chat.render ());
    }

}




