package windows_launchers;

import controllers.AbstractController;

public class RegisterWindow extends AbstractWindow
{
    public RegisterWindow(AbstractController controller)
    {
        super(controller);
    }

    @Override
    protected String fxmlFileName()
    {
        return "Registration.fxml";
    }
}
