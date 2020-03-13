package windows_launchers;

import controllers.AbstractController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public abstract class AbstractWindow {
    private final AbstractController controller;

    public AbstractWindow(AbstractController controller) {
        this.controller = controller;
    }
    public Pane root() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(url());
        loader.setController(controller);
        return loader.load(url().openStream());
    }

    private URL url() {
        System.out.println("/fxml/"+ fxmlFileName());
        return getClass().getResource("/fxml/"+ fxmlFileName());
    }
    public boolean resizable() {
        return false;
    }
    protected abstract String fxmlFileName();

}
