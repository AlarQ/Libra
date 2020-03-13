package model.elements;

import controllers.UserController;
import javafx.scene.control.Alert;
import model.HibernateUtil;
import model.Main;
import model.validations.LoginValidation;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

public class AdminUser extends User
{
    //delete users
    //change theme of app: light, dark

    public void changeTheme(String css)
    {
        Main.setCss(css);
    }

}
