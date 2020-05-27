package model;

import javafx.scene.control.Alert;
import model.elements.Book;
import model.elements.BookUser;
import model.elements.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.logging.Level;

public class HibernateUtil
{
    private static SessionFactory sessionFactory = null;

    public static void loadSessionFactory() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(BookUser.class);
        ServiceRegistry srvcReg = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(srvcReg);
    }

    public static Session getSession() throws HibernateException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
        } catch (Throwable t) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Exception while getting session");
            alert.showAndWait();
            t.printStackTrace();
        }
        if (session == null) {
            System.out.println("session is discovered null");
        }
        return session;
    }
}