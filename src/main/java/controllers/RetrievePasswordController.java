package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.HibernateUtil;
import model.elements.User;
import org.hibernate.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RetrievePasswordController implements Initializable
{
    @FXML
    private Button btnRetrieve;
    @FXML
    private TextField txtEmail;
    @FXML
    private Label lblEmail;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    //zmienić to w metodę getUserByEmail
    public void retrievePassword()
    {

        //HIBERNATE  SIĘ SPROWA
        if (User.getUserByEmail(txtEmail.getText()) == null)
            lblEmail.setText("email doesn't exist");
        else
            lblEmail.setText("email exists");
    }
}
