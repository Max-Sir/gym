package by.epam.gym.util;

import by.epam.gym.entities.user.User;
import by.epam.gym.utils.UserDataMatcher;
import org.junit.Assert;
import org.junit.Test;

public class UserDataMatcherTest {

    @Test
    public void shouldLoginBeValid(){
        String login = "admin1";
        boolean result = UserDataMatcher.match(login,UserDataMatcher.LOGIN_PATTERN);

        Assert.assertTrue(result);
    }

    @Test
    public void shouldLoginBeNotValid(){
        String login = "!Login@";
        boolean result = UserDataMatcher.match(login,UserDataMatcher.LOGIN_PATTERN);

        Assert.assertFalse(result);
    }
}
