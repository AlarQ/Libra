package model.elements;

import model.Main;

public class AdminUser extends User
{
    //delete users
    //change theme of app: light, dark

    public void changeTheme(String css) {
        Main.setCss(css);
    }

}
