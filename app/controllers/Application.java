package controllers;

import models.User;
import play.mvc.*;

import views.html.*;

import java.util.List;

import static play.libs.Json.toJson;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Heeeej nasa mega apliacia"));
    }

    @Security.Authenticated(SecuredUser.class)
    public static Result listUsers() {
        List<User> users = User.find.all();
        return ok(toJson(users));
    }

    public static Result oAuthDenied(String provider) {
        return ok("OAuth denied");
    }
}
