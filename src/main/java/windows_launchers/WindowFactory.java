package windows_launchers;

import controllers.LoginController;
import controllers.RegistrationController;
import controllers.RetrievePasswordController;
import controllers.UserController;

public enum WindowFactory {
    LOGIN {
        @Override
        public AbstractWindow createWindow(ViewHandler viewHandler) {
            return new LoginWindow(new LoginController(viewHandler));
        }
    },
    RETRIEVE {
        @Override
        public AbstractWindow createWindow(ViewHandler viewHandler) {
            return new RetrieveWindow(new RetrievePasswordController(viewHandler));
        }
    },
    REGISTER {
        @Override
        public AbstractWindow createWindow(ViewHandler viewHandler) {
            return new RegisterWindow(new RegistrationController(viewHandler));
        }
    },
    USER {
        @Override
        public AbstractWindow createWindow(ViewHandler viewHandler) {
            return new UserWindow(new UserController(viewHandler));
        }
    };

    public abstract AbstractWindow createWindow(ViewHandler viewHandler);
}