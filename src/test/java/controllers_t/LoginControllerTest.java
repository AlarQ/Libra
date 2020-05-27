package controllers_t;

import model.HibernateUtil;
import model.validations.LoginValidation;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class LoginControllerTest
{
    {
        HibernateUtil.loadSessionFactory();
    }

    @Test
    public void shouldReturnTrueForExistingUser() {
        String login = "a";
        String pass = "b";
        try {
            Assert.assertTrue(LoginValidation.isValidLogin(login, pass));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
