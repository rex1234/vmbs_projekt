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
import views.html.board;
import views.html.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static play.data.Form.form;

public class Application extends Controller {
    public static List<ActorWrap> actors = new ArrayList<ActorWrap>();

    private static Gson gson = new Gson();

    public static Result index() {
        return ok(login.render());
    }

    public static Result board(String id) {
        Member member;
        String userId = session().get("userId");
        if(userId == null) {
            member = new Member();
            member.setName(String.valueOf(new Random().nextInt()));
            member.setBoardId(Long.valueOf(id));
            RestService.getInstance().createMember(member);
            session().put("userId", member.getId().toString());
        } else {
            member = RestService.getInstance().getMemberForBoard(Long.valueOf(id), Long.valueOf(userId));
        }
        session().put("boardId", id);
        broadcast("users", id);
        return ok(board.render(member.getName()));
    }

    public static Result newBoard() {
        DynamicForm dynamicForm = form().bindFromRequest();
        Board board = new Board();
        board.setBoardType(BoardType.POKER_PLANNING);
        board.setName(dynamicForm.get("boardName"));

        RestService.getInstance().createBoard(board);

        Member member = new Member();
        member.setBoardId(board.getId());
        member.setName(dynamicForm.get("userName"));
        RestService.getInstance().createMember(member);
        session().put("userId", member.getId().toString());
        session().put("boardId", board.getId().toString());

        return redirect("/board/" + board.getId());
    }

    public static Result updateUsername() {
        Member member = RestService.getInstance().getMemberForBoard(Long.valueOf(session().get("boardId")), Long.valueOf(session().get("userId")));
        member.setName(form().bindFromRequest().get("newUsername"));
        System.out.println(member.getName());
        RestService.getInstance().updateMember(member);
        broadcast(member.getBoardId() + ":users");
        return ok();
    }

    public static Result createCard(String boardId) {
        try {
            Item item = form(Item.class).bindFromRequest().get();
            item.setBoardId(Long.valueOf(boardId));
            RestService.getInstance().createItem(item);
            broadcast("items", boardId);

            int itemCount = RestService.getInstance().getItemsForBoard(Long.valueOf(boardId)).size();
            int memberCount = RestService.getInstance().getMembersForBoard(Long.valueOf(boardId)).size();
            broadcast("reveal", boardId);
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
    public static void broadcast(String message, String id){
        for (ActorWrap actor : actors ) {
            if(actor.cmp(id))
                actor.actor.tell(message, null);
        }
    }

}
