package controllers;

import models.Board;
import play.mvc.Controller;
import play.mvc.Result;
import services.RestService;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        for (Board board : RestService.getInstance().getBoards()) {
            RestService.getInstance().deleteBoard(board.getId());
        }
        return ok(index.render("Heeeej nasa mega apliacia"));
    }
}
