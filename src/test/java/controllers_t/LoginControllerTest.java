package controllers_t;

import model.HibernateUtil;
import org.junit.Assert;
import org.junit.Test;
import validations.LoginValidation;

import java.sql.SQLException;

public class LoginControllerTest
{
    {
        HibernateUtil.loadSessionFactory();
    }

    @Test
    public void shouldReturnTrueForExistingUser()
    {
        String login = "a";
        String pass = "b";
        try
        {
            Assert.assertEquals(true, LoginValidation.isValidLogin(login, pass));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
