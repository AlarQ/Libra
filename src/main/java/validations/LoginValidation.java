package validations;

import model.HibernateUtil;
import model.elements.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginValidation
{
    Connection connection;
    private static User actualUser;

    public LoginValidation()
    {
    }

    public static void setActualUser(User actualUser)
    {
        LoginValidation.actualUser = actualUser;
    }

    public static User getActualUser()
    {
        return actualUser;
    }

    public static boolean isValidLogin(String userName, String password) throws SQLException
    {
        List<User> result = new ArrayList();

        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        //checking for given user
        result = (session.createQuery("FROM User u " +
                "WHERE u.login='" + userName +
                "' AND u.password='" + password + "'")
                .getResultList());

        try
        {
            actualUser = (User) result.get(0);
        } catch (IndexOutOfBoundsException e)
        {
            System.out.println("No match in database");
        }
        System.out.println(result.size());

        System.out.println(actualUser);
        session.getTransaction().commit();
        return result.size() != 0;

    }

}
