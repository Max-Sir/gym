package by.epam.gym.utils;

import by.epam.gym.entities.order.OrderDurationType;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Util class to calculate order's data.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.order.Order
 */
public class OrderCalculator {

    private static final long MONTH_MILLI_SECONDS_COUNT = 2_592_000_000L;
    private static final long HALF_YEAR_MILLI_SECONDS_COUNT = 15_724_800_000L;
    private static final long YEAR_MILLI_SECONDS_COUNT = 31_536_000_000L;

    private static final int PERCENT_INDEX = 100;
    private static final int ROUND_INDEX = 2;

    /**
     * This method calculates order's end date.
     *
     * @param duration     the order's duration.
     * @param purchaseDate the purchase date.
     * @return the end date.
     */
    public Date calculateEndDate(OrderDurationType duration, Date purchaseDate) {
        long time = purchaseDate.getTime();
        Date endDate = null;

        switch (duration) {
            case YEAR: {
                long result = time + YEAR_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
            case HALF_YEAR: {
                long result = time + HALF_YEAR_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
            case MONTH: {
                long result = time + MONTH_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
        }

        return endDate;
    }

    /**
     * This method calculate order's price with discount.
     *
     * @param price    the price.
     * @param discount the discount.
     * @return the calculated price.
     */
    public BigDecimal calculatePrice(BigDecimal price, int discount) {

        BigDecimal discountValue = price.multiply(new BigDecimal(discount)).divide(new BigDecimal(PERCENT_INDEX), ROUND_INDEX);

        return price.subtract(discountValue);
    }
}