package services;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import controllers.Application;

/**
 * Created by Rajci on 21.4.2015.
 */
public class MyWebSocketActor extends UntypedActor {

    public static Props props(ActorRef out, ActorWrap actor) {
        return Props.create(MyWebSocketActor.class, out , actor);
    }
    public String id;
    private final ActorRef out;
    public ActorWrap actor;

    public MyWebSocketActor(ActorRef out, ActorWrap actor) { // zalozenie actora
        this.out = out;   // vystupny socket
        this.actor = actor; // identifikacne cislo boardu
    }
    public akka.actor.ActorRef GetActor(){
        return self();
    }


    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String mess = (String) message;
            if(mess.startsWith("1")){
                actor.changeId(mess.substring(1));
            }else{
                out.tell(mess, self());
            }
        }
    }
}

