package Model;

import Model.Elements.Book;
import Model.Elements.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{

    private static SessionFactory sessionFactory = null;

    public static void loadSessionFactory()
    {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();
    }

    public static Session getSession() throws HibernateException
    {
        Session session = null;
        try
        {
            session = sessionFactory.openSession();
        } catch (Throwable t)
        {
            System.out.println("Exception while getting session.. ");
            t.printStackTrace();
        }
        if (session == null)
        {
            System.out.println("session is discovered null");
        }

        return session;
    }

}