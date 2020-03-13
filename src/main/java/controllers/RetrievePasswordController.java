package controllers;
import javafx.event.ActionEvent;
import model.validations.SendMail;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.elements.User;
import windows_launchers.AppViewHandler;
import windows_launchers.ViewHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RetrievePasswordController extends AbstractController
{
    @FXML private Button btnRetrieve;
    @FXML private TextField txtEmail;
    @FXML private Label lblEmail;
    @FXML private Button btnBackToLogin;

    public RetrievePasswordController(ViewHandler viewHandler)
    {
        super(viewHandler);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        btnBackToLogin.setOnAction(this::goBackToLogin);
    }

    public void retrievePassword()
    {
        User user = User.getUserByEmail(txtEmail.getText());
        if (user == null)
            lblEmail.setText("email doesn't exist");
        else {
            SendMail.sendMail("libra.service.supp@gmail.com",user);
            lblEmail.setText("We send you email with your password:");
        }
    }

    public void goBackToLogin(ActionEvent event)
    {
        try {
            new AppViewHandler().launchLoginWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
