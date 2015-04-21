package controllers;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.google.gson.Gson;
import models.Board;
import models.BoardType;
import models.Item;
import models.Member;
import play.data.DynamicForm;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import services.ActorWrap;
import services.MyWebSocketActor;
import services.RestService;
import views.html.index;

import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;

public class Application extends Controller {
    private  Application app = this;
    public static List<ActorWrap> actors = new ArrayList<ActorWrap>();

    private static Gson gson = new Gson();

    public static Result index() {
        return ok(index.render("Heeeej nasa mega apliacia"));
    }

    public static Result board(String id) {
        broadcast(id + ":users");
        return play.mvc.Results.TODO;
    }

    public static Result newBoard() {
        DynamicForm dynamicForm = form().bindFromRequest();
        Board board = new Board();
        board.setBoardType(BoardType.POKER_PLANNING);
        board.setName(dynamicForm.get("boardName"));

        Member member = new Member();
        member.setBoardId(board.getId());
        member.setName(dynamicForm.get("userName"));

        RestService.getInstance().createBoard(board);
        RestService.getInstance().createMember(member);

        return redirect("/board/" + board.getId());
    }

    public static Result updateUsername() {
        Member member = form(Member.class).bindFromRequest().get();
        RestService.getInstance().updateMember(member);
        return ok();
    }

    public static Result createCard(String boardId) {
        try {
            Item item = form(Item.class).bindFromRequest().get();
            item.setBoardId(Long.valueOf(boardId));
            RestService.getInstance().createItem(item);
            return ok();
        } catch (NumberFormatException e) {
            return internalServerError();
        }
    }

    public static Result pickedCards(String boardId) {
        try{
            return ok(gson.toJson(RestService.getInstance().getItemsForBoard(Long.valueOf(boardId))));
        } catch (NumberFormatException e) {
            return internalServerError();
        }
    }

    public static Result usersForBoard(String boardId) {
        try {
            return ok(gson.toJson(RestService.getInstance().getMembersForBoard(Long.valueOf(boardId))));
        } catch (NumberFormatException e) {
            return internalServerError();
        }
    }

    /////////////////////////////
    public static WebSocket<String> socket() {
        return WebSocket.withActor(new F.Function<ActorRef, Props>() {
            public Props apply(ActorRef out) throws Throwable {
                ActorWrap tmp = new ActorWrap(out,"unknown"); // pridanie actorov do pola
                actors.add(tmp);
                return MyWebSocketActor.props(out,tmp);
            }
        });
    }
    public static void broadcast(String message){
        for (ActorWrap actor : actors ) {
             actor.actor.tell(message, null);
        }
    }
    public static void broadcast(String message,String id){
        for (ActorWrap actor : actors ) {
            if(actor.cmp(id))
                actor.actor.tell(message, null);
        }
    }

}
