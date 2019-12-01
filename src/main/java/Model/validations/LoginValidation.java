package Model.validations;

import Model.Elements.Book;
import Model.Elements.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
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

    public boolean isValidLogin(String userName, String password) throws SQLException
    {
        //create SessionFactory
        //file name is optional if it is the same like "hibernate.cfg.xml"
        List<User> result;
        try (SessionFactory factory = new Configuration()
                .configure()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Book.class)
                .buildSessionFactory())
        {
            //get a new session and start a transaction
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            //checking for given user
            result = session.createQuery("FROM User u " +
                    "WHERE u.login='" + userName +
                    "' AND u.password='" + password + "'")
                    .getResultList();

            try
            {
                actualUser = result.get(0);
            } catch (IndexOutOfBoundsException e)
            {
                System.out.println("No match in database");
            }
            System.out.println(result.size());

            System.out.println(actualUser);

            //commit transaction
            session.getTransaction().commit();
            return result.size() != 0;
        }

    }
}
