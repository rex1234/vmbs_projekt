package controllers;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import static play.mvc.Http.Context.Implicit.session;


/**
 * Created by MiHu on 21.1.2015.
 */
public class SecuredUser extends Security.Authenticator {

    @Override
    public String getUsername(final Http.Context ctx) {
        final AuthUser u = PlayAuthenticate.getUser(session());
        if (u == null) return null;
        return u.getId();
    }

    @Override
    public Result onUnauthorized(final Http.Context ctx) {
        return redirect(routes.Application.index());
    }

}
