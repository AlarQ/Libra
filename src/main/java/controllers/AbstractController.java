package controllers;

import javafx.fxml.Initializable;
import windows_launchers.ViewHandler;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractController implements Initializable
{
    protected final ViewHandler viewHandler;

    @Override
    public abstract void initialize(URL location, ResourceBundle resources);

    public AbstractController(ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
    }

    public AbstractController() {
        viewHandler = null;
    }

    ;
}
