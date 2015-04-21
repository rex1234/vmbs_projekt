package controllers;

import akka.actor.ActorRef;
import akka.actor.Props;
import models.Board;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import services.MyWebSocketActor;
import services.RestService;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        for (Board board : RestService.getInstance().getBoards()) {
            RestService.getInstance().deleteBoard(board.getId());
        }
        return ok(index.render("Heeeej nasa mega apliacia"));
    }
    public static WebSocket<String> socket() {
        return WebSocket.withActor(new F.Function<ActorRef, Props>() {
            public Props apply(ActorRef out) throws Throwable {
                return MyWebSocketActor.props(out);
            }
        });
    }
}
