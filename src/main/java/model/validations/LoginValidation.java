package model.validations;

import controllers.UserController;
import model.HibernateUtil;
import model.elements.Book;
import model.elements.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LoginValidation
{
    Connection connection;
    private static User actualUser;

    public LoginValidation() {
    }

    public static void setActualUser(User actualUser) {
        LoginValidation.actualUser = actualUser;
    }

    public static User getActualUser() {
        return actualUser;
    }

    public static boolean isValidLogin(String userName, String password) throws SQLException {
        List result;

        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM User u " +
                "WHERE u.login=:userName " +
                "AND u.password=:password");
        query.setParameter("userName",userName);
        query.setParameter("password",password);
        result = query.getResultList();

        try {
            actualUser = (User) result.get(0);
            UserController.setActualUser(actualUser);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No match in database");
        }

        session.getTransaction().commit();
        session.close();

        return result.size() != 0;
    }
}
