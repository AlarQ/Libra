package model;

import model.elements.Book;
import model.elements.BookUser;
import model.elements.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil
{

    private static SessionFactory sessionFactory = null;

    public static void loadSessionFactory()
    {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(BookUser.class);
        ServiceRegistry srvcReg = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(srvcReg);
    }

    public static Session getSession() throws HibernateException
    {
        Session session = null;
        try {
            session = sessionFactory.openSession();
        } catch (Throwable t) {
            System.out.println("Exception while getting session.. ");
            t.printStackTrace();
        }
        if (session == null) {
            System.out.println("session is discovered null");
        }
        return session;
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
}