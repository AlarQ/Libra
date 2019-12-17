package model.validations;

import model.elements.User;
import org.apache.commons.validator.EmailValidator;

public class RegistrationValidation
{
    public boolean isPasswordValid(User user)
    {
        if (user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))
            return true;
        else
        {
            System.out.println("Password isn't valid");
            /*
            111asAS#aa
            The password policy is:
            At least 8 chars
            Contains at least one digit
            Contains at least one lower alpha char and one upper alpha char
            Contains at least one char within a set of special chars (@#%$^ etc.)
            Does not contain space, tab, etc.
             */
            return false;
        }
    }

    public boolean isEmailValid(User user)
    {
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (emailValidator.isValid(user.getEmail()))
            return true;
        else
            System.out.println("Email isn't valid");
        return false;
    }


}
