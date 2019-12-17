package model;

import org.hibernate.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.elements.*;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManager;
import javax.xml.crypto.dsig.TransformService;
import java.util.Date;


public class Main extends Application
{
    private static Stage primaryStage;

    public static void main(String[] args)
    {
        HibernateUtil.loadSessionFactory();
        System.out.println("***********************************************************************");

        Book book1 = new Book();
        book1.setTitle("f");
        Book book2 = new Book();
        book2.setTitle("f");

        User user = new User();
        user.setLogin("f");

        BookUser bookUser = new BookUser();

        bookUser.setBook(book1);
        bookUser.setUser(user);
        bookUser.setDate(new Date());

        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.save(bookUser);
        tx.commit();
        session.close();
        launch(args);
        //      System.out.println(bookUser.getBook().getTitle());

    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        primaryStage.setTitle("Libra 1.0");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        System.out.println("show");
    }

    public static Stage getPrimaryStage()
    {
        return primaryStage;
    }
}
