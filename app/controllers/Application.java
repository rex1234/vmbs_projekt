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
import services.MyWebSocketActor;
import services.RestService;
import views.html.index;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;

public class Application extends Controller {
    public static List<ActorRef> actors = new ArrayList<ActorRef>();

    private static Gson gson = new Gson();

    public static Result index() {
        return ok(index.render("Heeeej nasa mega apliacia"));
    }

    public static WebSocket<String> socket() {
        return WebSocket.withActor(new F.Function<ActorRef, Props>() {
            public Props apply(ActorRef out) throws Throwable {
                actors.add(out); // pridanie actorov do pola
                return MyWebSocketActor.props(out);
            }
        });
    }
    public static Result Update(){
        broadcast(" 1");
        return ok();
    }

    public static int broadcast(String d){
        for (ActorRef o : actors ) {
            o.tell(d,null);
        }
        return 0;
    }
    public static Result board(String id) {

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

    public static Result createCard() {
        Item item = form(Item.class).bindFromRequest().get();
        RestService.getInstance().createItem(item);
        return ok();
    }

    //public static

    public static Result usersForBoard(String boardId) {
        try {
            return ok(gson.toJson(RestService.getInstance().getMembersForBoard(Long.valueOf(boardId))));
        } catch (NumberFormatException e) {
            return internalServerError();
        }
    }
}
