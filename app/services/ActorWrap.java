package services;

/**
 * Created by Rajci on 21.4.2015.
 */
public class ActorWrap {
    public akka.actor.ActorRef actor;
    public String board;
    public ActorWrap(akka.actor.ActorRef act,String id){
            actor = act;
            board = id;
    }
    public Boolean cmp(String id){
        return board.contentEquals(id);
    }
    public void changeId(String id){
        board = id;
    }

}
