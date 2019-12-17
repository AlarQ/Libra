package controllers;

import model.Main;
import model.validations.LoginValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    private LoginValidation loginModel = new LoginValidation();

    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private Label lblRegister;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnLogin;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    public void createAccount(ActionEvent event) throws IOException
    {
        Stage primaryStage = Main.getPrimaryStage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("/fxml/Registration.fxml").openStream());
        primaryStage.setTitle("User Window");
        Scene scene = new Scene(root);
        String css = LoginController.class.getResource("/fxml/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void loginButtonClicked() throws IOException, SQLException
    {
        if (LoginValidation.isValidLogin(txtUserName.getText(), txtPassword.getText()))
        {
            Stage primaryStage = Main.getPrimaryStage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/fxml/UserAccount.fxml").openStream());
            primaryStage.setTitle("User Window");
            Scene scene = new Scene(root);
            String css = LoginController.class.getResource("/fxml/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.show();
        } else
            isConnected.setText("Incorrect login and/or password");
    }

    public LoginValidation getLoginModel()
    {
        return loginModel;
    }

    public Label getIsConnected()
    {
        return isConnected;
    }

    public TextField getTxtUserName()
    {
        return txtUserName;
    }

    public TextField getTxtPassword()
    {
        return txtPassword;
    }

    public Label getLblRegister()
    {
        return lblRegister;
    }

    public Button getBtnRegister()
    {
        return btnRegister;
    }

    public Button getBtnLogin()
    {
        return btnLogin;
    }
}
