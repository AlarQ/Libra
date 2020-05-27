package windows_launchers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Main;

import java.io.IOException;

public class AppViewHandler implements ViewHandler
{

    @Override
    public void launchLoginWindow() throws IOException {
        buildAndShowScene(Main.getPrimaryStage(), WindowFactory.LOGIN.createWindow(this));
    }

    @Override
    public void launchRetrieveWindow() throws IOException {
        buildAndShowScene(Main.getPrimaryStage(), WindowFactory.RETRIEVE.createWindow(this));
    }


    @Override
    public void launchRegisterWindow() throws IOException {
        buildAndShowScene(Main.getPrimaryStage(), WindowFactory.REGISTER.createWindow(this));
    }

    @Override
    public void launchUserWindow() throws IOException {
        buildAndShowScene(Main.getPrimaryStage(), WindowFactory.USER.createWindow(this));
    }

    private void buildAndShowScene(Stage stage, AbstractWindow window) throws IOException {

        stage.setTitle("Sign in");
        stage.setResizable(window.resizable());
        stage.setScene(new Scene(window.root()));
        stage.show();
    }

}
