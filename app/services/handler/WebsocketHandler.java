package services.handler;

import akka.actor.*;



public class WebsocketHandler extends AbstractActor {

    private final ActorRef out;


    public static Props props(ActorRef out){
        return Props.create ( WebsocketHandler.class, out );
    }

    public WebsocketHandler(ActorRef out) {
        this.out = out;
    }


  @Override
    public Receive createReceive(){
        return receiveBuilder ().match ( String.class, message->
                out.tell ( "I received your message" + message, self ()) ).build ();
  }
}