package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Main;
import model.elements.User;
import model.validations.LoginValidation;
import model.validations.RegistrationValidation;
import windows_launchers.AppViewHandler;
import windows_launchers.ViewHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegistrationController extends AbstractController
{
    @FXML
    private Label lblLogin;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblEmail;
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnAddUser;
    @FXML
    private Button btnBackToLogin;

    public RegistrationController(ViewHandler viewHandler)
    {
        super(viewHandler);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        btnAddUser.setOnAction(this::confirmRegistration);
        btnBackToLogin.setOnAction(this::goBackToLogin);
    }


    public void confirmRegistration(ActionEvent event)
    {
        //dodać przypoadek jak login jest za krótki
        User user = new User(txtLogin.getText(), txtPassword.getText(), txtEmail.getText());
        RegistrationValidation model = new RegistrationValidation(user);
        boolean flag = true;
        boolean alertNeeded = false;
        boolean[] valid = model.whatIsValid();

        //login is valid
        if (valid[0])
            lblLogin.setText("Login correct!");
        else{
            lblLogin.setText("User exist, chaneg login");
            flag = false;
        }

        //password is valid
        if (valid[1])
            lblPassword.setText("Password correct!");
        else {
            lblPassword.setText("Password is not valid!");
            alertNeeded = true;
            flag = false;
        }
        //email is valid
        if (valid[2])
            lblEmail.setText("Email correct!");
        else{
            lblEmail.setText("Email is not valid!");
            flag = false;
        }

        if (alertNeeded)
            showPassAlert();

        if (flag) {
            user.addUser();
            finalizeReg(event);
        }
    }

    private void finalizeReg(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration");
        alert.setContentText("You registered successfully!!!");
        alert.showAndWait();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    private void showPassAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hints");
        alert.setHeaderText("Registration rules");
        alert.setContentText(" " +
                "Login should be at least 5 signs long\n" +
                "**************************************\n" +
                "The password policy is:\n" +
                "- At least 5 chars\n" +
                "- Contains at least one digit\n" +
                "- Contains at least one lower alpha char and one upper alpha char\n" +
                "- Does not contain space, tab, etc.");
        alert.showAndWait();
    }

    public void goBackToLogin(ActionEvent event)
    {
        try {
            new AppViewHandler().launchLoginWindow();
        } catch (IOException e) {
            //alert box
            e.printStackTrace();
        }
    }
}
