package controllers;

import model.Main;
import model.validations.LoginValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import windows_launchers.AppViewHandler;
import windows_launchers.ViewHandler;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController extends AbstractController
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
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void createAccount(ActionEvent event) throws IOException {
        new AppViewHandler().launchRegisterWindow();
    }

    public void retrievePassword() throws IOException {
        new AppViewHandler().launchRetrieveWindow();
    }


    public void loginButtonClicked() throws IOException, SQLException {
        if (LoginValidation.isValidLogin(txtUserName.getText(), txtPassword.getText())) {
            new AppViewHandler().launchUserWindow();
        } else
            isConnected.setText("Incorrect login and/or password");
    }

    public LoginController(ViewHandler viewHandler) {
        super(viewHandler);
    }

}
