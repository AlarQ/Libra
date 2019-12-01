package Controller;

import Model.validations.LoginValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    public void login(ActionEvent event) throws SQLException, IOException
    {
        /**
         if (loginModel.isValidLogin(txtUserName.getText(), txtPassword.getText()))
         {
         isConnected.setText("Successful ");
         Stage primaryStage = new Stage();
         ((Node) event.getSource()).getScene().getWindow().hide();
         FXMLLoader loader = new FXMLLoader();
         Pane root = loader.load(getClass().getResource("/fxml/User.fxml").openStream());
         UserController userController = (UserController) loader.getController();
         userController.setLblUser("Welcome " + txtUserName.getText() + "!");
         primaryStage.setTitle("User Window");
         primaryStage.setScene(new Scene(root));
         primaryStage.show();
         } else
         isConnected.setText("login or/and password are not valid!");
         */
    }

    public void register(ActionEvent event) throws IOException
    {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("/fxml/Registration.fxml").openStream());
        primaryStage.setTitle("User Window");
        Scene scene = new Scene(root);
        String css = LoginController.class.getResource("/fxml/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
