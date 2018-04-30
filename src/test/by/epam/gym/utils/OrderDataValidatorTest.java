package by.epam.gym.utils;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Date;
import java.time.LocalDate;

@RunWith(DataProviderRunner.class)
public class OrderDataValidatorTest {

    private static final OrderDataValidator ORDER_DATA_VALIDATOR = new OrderDataValidator();
    private static String currentDateValue;

    @BeforeClass
    public static void setCurrentDateValue() {
        Date currentDate = Date.valueOf(LocalDate.now());
        currentDateValue = String.valueOf(currentDate);
    }

    @DataProvider
    public static Object[][] validData() {


        return new Object[][]{
                {currentDateValue, "MONTH", "1"},
                {currentDateValue, "MONTH", "0"},
                {currentDateValue, "HALF_YEAR", "1"},
                {currentDateValue, "YEAR", "1"}
        };
    }

    @DataProvider
    public static Object[][] notValidData() {
        return new Object[][]{
                {"", "MONTH", "1"},
                {null, "MONTH", "0"},
                {currentDateValue, "", "1"},
                {currentDateValue, null, "1"},
                {currentDateValue, "ALL_LIFE", "1"},
                {currentDateValue, "MONTH", "2"},
                {currentDateValue, "HALF_YEAR", ""},
                {currentDateValue, "YEAR", null}
        };
    }

    @DataProvider
    public static Object[][] validFeedBacks() {
        return new Object[][]{
                {"Feedbakc"},
                {"all is goof"},
                {"all is good, but at he 15-00 trainer was bad."}
        };
    }

    @DataProvider
    public static Object[][] notValidFeedBacks() {
        return new Object[][]{
                {""},
                {null},
                {"all is good,<alert(user.password)> but at he 15-00 trainer was bad."}
        };
    }

    @Test
    @UseDataProvider("validFeedBacks")
    public void shouldFeedbackBeValid(String feedback) {
        boolean actualResult = ORDER_DATA_VALIDATOR.checkFeedback(feedback);

        Assert.assertTrue(actualResult);
    }

    @Test
    @UseDataProvider("notValidFeedBacks")
    public void shouldFeedbackBeNotValid(String feedback) {
        boolean actualResult = ORDER_DATA_VALIDATOR.checkFeedback(feedback);

        Assert.assertFalse(actualResult);
    }

    @Test
    @UseDataProvider("validData")
    public void shouldOrderDataValidationBeSuccessful(String purchaseDateValue, String durationValue, String isPersonalTrainerNeedValue) {
        boolean actualResult = ORDER_DATA_VALIDATOR.checkOrderData(purchaseDateValue, durationValue, isPersonalTrainerNeedValue);

        Assert.assertTrue(actualResult);
    }

    @Test
    @UseDataProvider("notValidData")
    public void shouldOrderDataValidationFail(String purchaseDateValue, String durationValue, String isPersonalTrainerNeedValue) {
        boolean actualResult = ORDER_DATA_VALIDATOR.checkOrderData(purchaseDateValue, durationValue, isPersonalTrainerNeedValue);

        Assert.assertFalse(actualResult);
    }
}
