package by.epam.gym.util;

import by.epam.gym.utils.UserDataValidator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDataValidatorTest {

    private static UserDataValidator userDataValidator;

    @BeforeClass
    public static void setUserDataValidator(){
        userDataValidator = new UserDataValidator();
    }

    @Test
    public void shouldUserDataBeValid(){
        String login = "client7";
        String password = "client7";
        String firstName = "Валя";
        String lastName = "Иванова";

        boolean result = userDataValidator.checkData(login,password,firstName,lastName);

        Assert.assertTrue(result);
    }

    @Test
    public void shouldUserDataBeNotValid(){
        String login = "admin1!!!";
        String password = "@admin_sad1992";
        String firstName = "Alex@3";
        String lastName = "Ivanov2";

        boolean result = userDataValidator.checkData(login,password,firstName,lastName);

        Assert.assertFalse(result);
    }

}
