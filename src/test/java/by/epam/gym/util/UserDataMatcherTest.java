package by.epam.gym.util;


import by.epam.gym.utils.UserDataMatcher;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class UserDataMatcherTest {

    @DataProvider
    public static Object[][] validLoginVariables() {
        String letterLogin = "login";
        String letterAndNumberLogin = "Login1992";
        String twoWordsLogin = "Login_login";

        boolean expectedResult = true;

        return new Object[][]{
                {letterLogin, expectedResult},
                {letterAndNumberLogin, expectedResult},
                {twoWordsLogin, expectedResult}
        };
    }

    @DataProvider
    public static Object[][] notValidLoginVariables() {
        String loginWithPunctuationSymbol = "login!";
        String loginWithSpace = "Login 1992";
        String loginAsEmail = "login@login.ru";

        boolean expectedResult = false;

        return new Object[][]{
                {loginWithPunctuationSymbol, expectedResult},
                {loginWithSpace, expectedResult},
                {loginAsEmail, expectedResult}
        };
    }

    @DataProvider
    public static Object[][] validNameVariables() {
        String shortName = "A";
        String longName = "Alexander";

        boolean expectedResult = true;

        return new Object[][]{
                {shortName, expectedResult},
                {longName, expectedResult}
        };
    }

    @DataProvider
    public static Object[][] notValidNameVariables() {
        String emptyName = "";
        String nameWithNumber = "Alexander2";
        String nameWithPunctuationSymbol = "Alex!";

        boolean expectedResult = false;

        return new Object[][]{
                {emptyName, expectedResult},
                {nameWithNumber, expectedResult},
                {nameWithPunctuationSymbol, expectedResult}
        };
    }

    @DataProvider
    public static Object[][] validPasswordVariables() {
        String shortPassword = "5051";
        String passwordWithLetter = "pass505";
        String passwordWithTwoWords = "pass_pass23";

        boolean expectedResult = true;

        return new Object[][]{
                {shortPassword, expectedResult},
                {passwordWithLetter, expectedResult},
                {passwordWithTwoWords, expectedResult}
        };
    }

    @DataProvider
    public static Object[][] notValidPasswordVariables() {
        String shortPassword = "505";
        String passwordWithPunctuationSymbol = "pass!!!505";
        String passwordWithSpace = "pass pass23";

        boolean expectedResult = false;

        return new Object[][]{
                {shortPassword, expectedResult},
                {passwordWithPunctuationSymbol, expectedResult},
                {passwordWithSpace, expectedResult}
        };
    }

    @Test
    @UseDataProvider("validLoginVariables")
    public void shouldLoginBeValid(String login, boolean expectedResult) {
        boolean currentResult = UserDataMatcher.match(login, UserDataMatcher.LOGIN_PATTERN);

        Assert.assertEquals(expectedResult, currentResult);
    }

    @Test
    @UseDataProvider("notValidLoginVariables")
    public void shouldLoginBeNotValid(String login, boolean expectedResult) {
        boolean currentResult = UserDataMatcher.match(login, UserDataMatcher.LOGIN_PATTERN);

        Assert.assertEquals(expectedResult, currentResult);
    }

    @Test
    @UseDataProvider("validNameVariables")
    public void shouldNameBeValid(String name, boolean expectedResult) {
        boolean currentResult = UserDataMatcher.match(name, UserDataMatcher.NAME_PATTERN);

        Assert.assertEquals(expectedResult, currentResult);
    }

    @Test
    @UseDataProvider("notValidNameVariables")
    public void shouldNameBeNotValid(String name, boolean expectedResult) {
        boolean currentResult = UserDataMatcher.match(name, UserDataMatcher.NAME_PATTERN);

        Assert.assertEquals(expectedResult, currentResult);
    }

    @Test
    @UseDataProvider("validPasswordVariables")
    public void shouldPasswordBeValid(String password, boolean expectedResult) {
        boolean currentResult = UserDataMatcher.match(password, UserDataMatcher.PASSWORD_PATTERN);

        Assert.assertEquals(expectedResult, currentResult);
    }

    @Test
    @UseDataProvider("notValidPasswordVariables")
    public void shouldPasswordBeNotValid(String password, boolean expectedResult) {
        boolean currentResult = UserDataMatcher.match(password, UserDataMatcher.PASSWORD_PATTERN);

        Assert.assertEquals(expectedResult, currentResult);
    }
}
