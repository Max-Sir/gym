package by.epam.gym.utils;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class UserDataValidatorTest {

    private static final UserDataValidator USER_DATA_VALIDATOR = new UserDataValidator();
    private static final String FULL_NAME = "Alex Smith";

    @DataProvider
    public static Object[][] notValidDataForNameForFullCheckOperation() {
        return new Object[][]{
                {""},
                {null},
                {"Alex "},
                {" Alex"},
                {" "},
                {"AlexSmith"},
                {"Alex-Smith"},
                {"Alex_Smith"},
                {"Alex Smith 22"},
                {"2Alex Smith"}
        };
    }

    @DataProvider
    public static Object[][] validUserData() {
        return new Object[][]{
                {"client1", "client1", "Alex", "Smith"},
                {"client1", "client1", "Александр", "Смит"},
                {"111111Client", "client1", "Alex", "Smith"},
                {"client1", "111111Client", "Alex", "Smith"},
                {"client1", "5051", "Alex", "Smith"},
                {"client1", "client1", "Ai", "Smith"},
                {"client1", "client1", "Alex", "Che"},
                {"client1_lone", "client1", "Alex", "Che"},
                {"client1", "client1_client1", "Alex", "Che"},
                {"CLIENT", "client1", "Alex", "Che"},
                {"client1", "CLIENT", "Alex", "Che"},
        };
    }

    @DataProvider
    public static Object[][] notValidUserData() {
        return new Object[][]{
                {"!!!client1", "client1", "Alex", "Smith"},
                {"client1", "client1!1", "Александр", "Смит"},
                {"111111Client", "client1", "", "Smith"},
                {"client1", "111111Client", "Alex", ""},
                {"client1", "5051", null, "Smith"},
                {"client1", "client1", "Ai", null},
                {"client1", "", "Alex", "Che"},
                {"client@mail.ru", "cli", "Alex", "Che"},
                {"client1", null, "Alex", "Che"},
                {"CL", "client1", "Alex", "Che"},
                {"C", "client1", "Alex", "Che"},
                {"CLs", "client1", "Alex", "Che"},
                {"CLsd", "client1", "Alex", "Che"},
                {"CLass", "client1", "Alex", "Che"},
                {null, "CLIENT", "Alex", "Che"},
                {"client1", "client1", "Alex!", "Smith"},
                {"client1", "client1", "Александр", "Смит2"},
                {"111111#Client", "client1", "Alex", "Smith"},
                {"client1", "11111+=+1Client", "Alex", "Smith"},
                {"client1", "5051", "<js>", "Smith"},
                {"client1", "client1", "Ai", "<>@"},
                {"client,.1", "client1", "Alex", "Che"},
                {"@@", "client1", "Alex", "Che"},
                {"client1", "ad", "Alex", "Che"},
                {"CLIENT", "a", "Alex", "Che"},
                {"client1", "CLIENT", "Alex", "Вал2"}
        };
    }

    @Test
    public void shouldCheckNameForFullBeSuccessful() {
        boolean actualResult = USER_DATA_VALIDATOR.isNameFull(FULL_NAME);

        Assert.assertTrue(actualResult);
    }

    @Test
    @UseDataProvider("notValidDataForNameForFullCheckOperation")
    public void shouldCheckNameForFullFail(String name) {
        boolean actualResult = USER_DATA_VALIDATOR.isNameFull(name);

        Assert.assertFalse(actualResult);
    }

    @Test
    @UseDataProvider("validUserData")
    public void shouldUserDataBeValid(String login, String password, String firstName, String lastName) {
        boolean actualResult = USER_DATA_VALIDATOR.checkData(login, password, firstName, lastName);

        Assert.assertTrue(actualResult);
    }

    @Test
    @UseDataProvider("notValidUserData")
    public void shouldUserDataBeNotValid(String login, String password, String firstName, String lastName) {
        boolean actualResult = USER_DATA_VALIDATOR.checkData(login, password, firstName, lastName);

        Assert.assertFalse(actualResult);
    }
}
