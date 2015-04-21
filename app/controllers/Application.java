package controllers;

import akka.actor.ActorRef;
import akka.actor.Props;
import models.Board;
import models.Member;
import play.data.DynamicForm;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import services.MyWebSocketActor;
import services.RestService;
import views.html.index;

import static play.data.Form.form;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Heeeej nasa mega apliacia"));
    }

    public static WebSocket<String> socket() {
        return WebSocket.withActor(new F.Function<ActorRef, Props>() {
            public Props apply(ActorRef out) throws Throwable {
                return MyWebSocketActor.props(out);
            }
        });
    }

    public static Result board(String id) {
        return play.mvc.Results.TODO;
    }

    public static Result newBoard() {
        DynamicForm dynamicForm = form().bindFromRequest();
        Board board = new Board();
        board.setName(dynamicForm.get("boardName"));

        Member member = new Member();
        member.setName(dynamicForm.get("userName"));

        RestService.getInstance().createBoard(board);
        RestService.getInstance().createMember(board.getId(), member);

        return redirect("/board/" + board.getId());
    }
}
