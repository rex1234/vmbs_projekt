package services;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Created by Rajci on 21.4.2015.
 */
public class MyWebSocketActor extends UntypedActor {

    public static Props props(ActorRef out) {
        return Props.create(MyWebSocketActor.class, out);
    }
    public String id;
    private final ActorRef out;

    public MyWebSocketActor(ActorRef out) {
        this.out = out;
    }
    public akka.actor.ActorRef GetActor(){
        return self();
    }


    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            if(((String) message).contentEquals("1")){
                out.tell("Dostal som spravu", self());
            }else{
                out.tell(message, self());
            }
        }

    }
}

