package services;

import com.feth.play.module.pa.providers.oauth2.OAuth2AuthInfo;
import com.feth.play.module.pa.providers.oauth2.OAuth2AuthUser;
import com.feth.play.module.pa.providers.oauth2.google.GoogleAuthUser;
import com.feth.play.module.pa.providers.oauth2.facebook.*;
import com.feth.play.module.pa.user.EmailIdentity;
import com.feth.play.module.pa.user.NameIdentity;
import models.User;
import play.Application;

import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.service.UserServicePlugin;

import java.util.Date;

import static play.mvc.Http.Context.Implicit.session;

public class MyUserServicePlugin extends UserServicePlugin {

    public MyUserServicePlugin(final Application app) {
        super(app);
    }

    @Override
    public Object save(final AuthUser authUser) {
        userDataToSession(authUser);
        if (authUser instanceof EmailIdentity) {
            final String email = ((EmailIdentity) authUser).getEmail();
            if (email != null) {
                session().put("email", email);
                (new User(authUser.getId(), email)).save();
            }
        }
        return authUser;
    }

    @Override
    public AuthUser update(final AuthUser knownUser) {
        userDataToSession(knownUser);
        return knownUser;
    }

    @Override
    public Object getLocalIdentity(final AuthUserIdentity identity) {
        return User.find.byId(identity.getId());
    }

    @Override
    public AuthUser merge(final AuthUser newUser, final AuthUser oldUser) {
      return null;
    }

    @Override
    public AuthUser link(final AuthUser oldUser, final AuthUser newUser) {
        return null;
    }

    private void userDataToSession(AuthUser authUser) {
        if (authUser instanceof GoogleAuthUser) {
            final String picture = ((GoogleAuthUser) authUser).getPicture();
            if (picture != null) {
                session().put("picture", picture);
            }
        }

        if (authUser instanceof NameIdentity) {
            final String name = ((NameIdentity) authUser).getName();
            if (name != null) {
                session().put("username", name);
            }
        }

        if (authUser instanceof FacebookAuthUser) {
            final String picture = ((FacebookAuthUser) authUser).getPicture();
            if (picture != null) {
                session().put("picture", picture);
            }
        }
    }
}