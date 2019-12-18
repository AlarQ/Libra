package model.validations;

import model.HibernateUtil;
import model.elements.User;
import org.hibernate.Session;

import java.util.List;

public class Exist
{
    public static boolean doesEmailExist(String email)
    {
        List result;
        User actualUser;
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        result = session.createQuery("FROM User u " +
                "WHERE u.email='" + email + "'")
                .getResultList();

        session.getTransaction().commit();
        return result.size() != 0;
    }
}
