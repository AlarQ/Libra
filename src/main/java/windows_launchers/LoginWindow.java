package windows_launchers;

import controllers.AbstractController;

public class LoginWindow extends AbstractWindow
{
    @Override
    protected String fxmlFileName()
    {
        return "Login.fxml";
    }

    public LoginWindow(AbstractController controller)
    {
        super(controller);
    }
}
