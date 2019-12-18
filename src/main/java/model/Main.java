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

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;

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
