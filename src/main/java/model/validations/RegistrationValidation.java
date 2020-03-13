package model.validations;

import controllers.UserController;
import model.HibernateUtil;
import model.elements.User;
import org.apache.commons.validator.EmailValidator;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class RegistrationValidation
{
    private User user;

    /**
     * The password policy is:
     * At least 5 chars
     * Contains at least one digit
     * Contains at least one lower alpha char and one upper alpha char
     * Does not contain space, tab, etc.
     */

    public static boolean isLoginValid(String login)
    {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM User u " +
                "WHERE u.login=:login");
        query.setParameter("login", login);
        List result = query.getResultList();

        session.getTransaction().commit();
        return result.isEmpty();
    }

    public boolean isPasswordValid(User user)
    {
        if (user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{5,}$"))
            return true;
        else {
            System.out.println("Password isn't valid");
            return false;
        }
    }

    public boolean isEmailValid(User user)
    {
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (emailValidator.isValid(user.getEmail()))
            return true;
        else
            System.out.println("Email isn't valid");
        return false;
    }

    public boolean[] whatIsValid()
    {
        boolean[] valid = {true,true,true};
        if (!RegistrationValidation.isLoginValid(user.getLogin()))
            valid[0] = false;

        if (!isPasswordValid(user))
            valid[1] = false;

        if (!isEmailValid(user))
            valid[2] = false;

        return valid;
    }

    public RegistrationValidation(User user)
    {
        this.user = user;
    }
}
