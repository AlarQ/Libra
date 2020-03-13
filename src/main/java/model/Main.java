package model;

import javafx.application.Application;
import javafx.stage.Stage;
import windows_launchers.AppViewHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application
{
    private static Stage primaryStage;
    private static String css;
    public static Properties properties = new Properties();

    public static void main(String[] args)
    {
        HibernateUtil.loadSessionFactory();
        System.out.println("***********************************************************************");

        launch(args);
        initProperties();
        System.out.println(properties.getProperty("path.image"));
    }

    @Override
    public void start(Stage prStage) throws Exception
    {
        this.primaryStage = prStage;
        new AppViewHandler()
                .launchLoginWindow();

    }

    public static void initProperties()
    {
        try {
            String fileName = "src/main/resources/config.properties";
            InputStream input =
                    new FileInputStream(fileName);
            if (input == null) {
                System.out.println("unable to find "+fileName);
            }
            properties.load(input);
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public static Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public static void setCss(String css_)
    {
        css = css_;
    }

    public static String getCss(){return css;}


}
