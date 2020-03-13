package windows_launchers;

import controllers.AbstractController;

public class RetrieveWindow extends AbstractWindow
{
    @Override
    protected String fxmlFileName()
    {
        return "RetrievePassword.fxml";
    }

    public RetrieveWindow(AbstractController controller)
    {
        super(controller);
    }
}
