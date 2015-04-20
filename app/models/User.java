package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by MiHu on 20.4.2015.
 */
@Entity
public class User extends Model {

    @Id
    public String id;
    public String email;

    public User() {
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public static Finder<String, User> find = new Finder<>(String.class, User.class);

}
