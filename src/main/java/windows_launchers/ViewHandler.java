package windows_launchers;

import java.io.IOException;

public interface ViewHandler
{
    void launchLoginWindow() throws IOException;

    void launchRetrieveWindow() throws IOException;

    void launchRegisterWindow() throws IOException;

    void launchUserWindow() throws IOException;
}