package by.epam.gym.utils;

import by.epam.gym.entities.order.OrderDurationType;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.sql.Date;

@RunWith(DataProviderRunner.class)
public class OrderCalculatorTest {

    private static final OrderCalculator ORDER_CALCULATOR = new OrderCalculator();

    @DataProvider
    public static Object[][] validDataForEndDateCalculateOperation() {
        return new Object[][]{
                {OrderDurationType.MONTH, Date.valueOf("2018-02-01"), Date.valueOf("2018-02-31")},
                {OrderDurationType.HALF_YEAR, Date.valueOf("2018-02-01"), Date.valueOf("2018-08-02")},
                {OrderDurationType.YEAR, Date.valueOf("2018-02-01"), Date.valueOf("2019-02-01")}
        };
    }

    @DataProvider
    public static Object[][] notValidDataForEndDateCalculateOperation() {
        return new Object[][]{
                {OrderDurationType.MONTH, Date.valueOf("2018-02-01"), Date.valueOf("2018-02-29")},
                {OrderDurationType.HALF_YEAR, Date.valueOf("2018-02-01"), Date.valueOf("2018-09-02")},
                {OrderDurationType.YEAR, Date.valueOf("2018-02-01"), Date.valueOf("2020-02-01")}
        };
    }

    @DataProvider
    public static Object[][] validDataForDiscountCalculateOperation() {
        return new Object[][]{
                {new BigDecimal(100), 10, new BigDecimal(90)},
                {new BigDecimal(100), 20, new BigDecimal(80)},
                {new BigDecimal(100), 50, new BigDecimal(50)}
        };
    }

    @DataProvider
    public static Object[][] notValidDataForDiscountCalculateOperation() {
        return new Object[][]{
                {new BigDecimal(100), 10, new BigDecimal(95)},
                {new BigDecimal(100), 20, new BigDecimal(81)},
                {new BigDecimal(100), 50, new BigDecimal(55)}
        };
    }

    @Test
    @UseDataProvider("validDataForEndDateCalculateOperation")
    public void shouldEndDateCalculateOperationBeSuccessful(OrderDurationType duration, Date startDate, Date endDate) {
        Date actualDate = ORDER_CALCULATOR.calculateEndDate(duration, startDate);

        Assert.assertEquals(endDate, actualDate);
    }

    @Test
    @UseDataProvider("notValidDataForEndDateCalculateOperation")
    public void shouldEndDateCalculateOperationFail(OrderDurationType duration, Date startDate, Date endDate) {
        Date actualDate = ORDER_CALCULATOR.calculateEndDate(duration, startDate);

        Assert.assertNotEquals(endDate, actualDate);
    }

    @Test
    @UseDataProvider("validDataForDiscountCalculateOperation")
    public void shouldDiscountCalculateOperationBeSuccessful(BigDecimal price, int discount, BigDecimal expectedPrice) {
        BigDecimal actualPrice = ORDER_CALCULATOR.calculatePrice(price, discount);

        Assert.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    @UseDataProvider("notValidDataForDiscountCalculateOperation")
    public void shouldDiscountCalculateOperationFail(BigDecimal price, int discount, BigDecimal expectedPrice) {
        BigDecimal actualPrice = ORDER_CALCULATOR.calculatePrice(price, discount);

        Assert.assertNotEquals(expectedPrice, actualPrice);
    }

}
