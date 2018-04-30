package by.epam.gym.util;

import by.epam.gym.utils.UserDataValidator;
import org.junit.Assert;
import org.junit.Test;

public class UserDataValidatorTest {

    @Test
    public void should(){
        UserDataValidator userDataValidator = new UserDataValidator();

        String name = "Магомед Магомедов";

        Assert.assertTrue(userDataValidator.isNameFull(name));
    }

}
