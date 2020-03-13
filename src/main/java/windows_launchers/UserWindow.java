package windows_launchers;

import controllers.AbstractController;

public class UserWindow extends AbstractWindow
{
    @Override
    protected String fxmlFileName()
    {
        return "UserAccount.fxml";
    }

    public UserWindow(AbstractController controller)
    {
        super(controller);
    }
}
