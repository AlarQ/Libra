package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.elements.User;
import model.validations.LoginValidation;
import model.validations.RegistrationValidation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable
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


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    //fix the bug, labels don't change
    public void confirmRegistration(ActionEvent event)
    {
        User user = new User(txtLogin.getText(), txtPassword.getText(), txtEmail.getText());
        RegistrationValidation model = new RegistrationValidation();
        boolean flag = true;
        try
        {
            if (LoginValidation.isValidLogin(user.getLogin(), user.getPassword()))
            {
                flag = false;
                lblLogin.setText("User exist, chaneg login");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (!model.isPasswordValid(user))
        {
            flag = false;
            lblPassword.setText("Password is not valid!");
        }
        if (!model.isEmailValid(user))
        {
            flag = false;
            lblEmail.setText("Email is not valid!");
        }

        if (flag)
        {
            user.addUser();
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
    }

}
