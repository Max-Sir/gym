package by.epam.gym.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.epam.gym.entities.order.OrderDurationType.*;

/**
 * Util class to validate order's data.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.order.Order
 */
public class OrderDataValidator {

    private static final String NOT_NEED_TRAINER_VALUE = "0";
    private static final String NEED_TRAINER_VALUE = "1";

    private static final String ORDER_FEEDBACK_PATTERN = ".*<.*>+.*";

    /**
     * This method checks input order's data.
     *
     * @param dateValue                  the purchase date value.
     * @param durationValue              the order's duration value.
     * @param isPersonalTrainerNeedValue the isPersonalTrainerNeed string value.
     * @return true if data is valid otherwise false.
     */
    public boolean checkOrderData(String dateValue, String durationValue, String isPersonalTrainerNeedValue) {

        return checkOrderPurchaseDate(dateValue) && checkOrderDurationValue(durationValue) && checkIsPersonalTrainerNeedValue(isPersonalTrainerNeedValue);

    }

    /**
     * This method checks feedback.
     *
     * @param feedback the feedback
     * @return true if feedback is valid and false otherwise.
     */
    public boolean checkFeedback(String feedback) {
        if (feedback == null) {
            return false;
        }
        if (feedback.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(ORDER_FEEDBACK_PATTERN);
        Matcher matcher = pattern.matcher(feedback);

        return !matcher.matches();
    }

    private boolean checkOrderPurchaseDate(String dateValue) {
        if (dateValue == null || dateValue.isEmpty()) {
            return false;
        }

        Date currentDate = Date.valueOf(LocalDate.now());
        Date purchaseDate = Date.valueOf(dateValue);

        return purchaseDate.getTime() >= currentDate.getTime();
    }

    private boolean checkOrderDurationValue(String durationValue) {

        if (String.valueOf(MONTH).equals(durationValue)) {
            return true;
        }

        if (String.valueOf(HALF_YEAR).equals(durationValue)) {
            return true;
        }

        if (String.valueOf(YEAR).equals(durationValue)) {
            return true;
        }

        return false;
    }

    private boolean checkIsPersonalTrainerNeedValue(String isPersonalTrainerNeedValue) {

        return NOT_NEED_TRAINER_VALUE.equals(isPersonalTrainerNeedValue) || NEED_TRAINER_VALUE.equals(isPersonalTrainerNeedValue);

    }
}
