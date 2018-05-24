package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.chat;


public class ChatController extends Controller {




    public Result chat() {
        /*String token = request().getQueryString ( "key" );
        if (token == null)
            token = authservice.getSecret ();
        Optional <String> referer = request().header ( "referer" );
        if (referer.isPresent()){
            Integer Id = getIdUrl(referer.get());
            if (Id == null)
                return forbidden ();
            Optional <String> httpsHeader = request ().header ( "X-Forwarded" );
            boolean secured = httpsHeader.isPresent () && httpsHeader.get ().contains ( "http" );*/



        return ok(chat.render ());

    }


}




