package by.epam.gym.utils;

import by.epam.gym.entities.order.OrderDurationType;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Util class to calculateEndDate order's end date.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.user.User
 */
public class OrderCalculator {

    private static final String PRICES_FILE_NAME = "prices";

    private static final String MONTH_PRICE = "price.duration.month";
    private static final String HALF_YEAR_PRICE = "price.duration.half_year";
    private static final String YEAR_PRICE = "price.duration.year";

    private static final String PERSONAL_TRAININGS_MONTH_PRICE = "price.personal_training.month";
    private static final String PERSONAL_TRAININGS_HALF_YEAR_PRICE = "price.personal_training.half_year";
    private static final String PERSONAL_TRAININGS_YEAR_PRICE = "price.personal_training.year";

    private static final long MONTH_MILLI_SECONDS_COUNT = 2_592_000_000L;
    private static final long HALF_YEAR_MILLI_SECONDS_COUNT = 15_724_800_000L;
    private static final long YEAR_MILLI_SECONDS_COUNT = 31_536_000_000L;

    public  Date calculateEndDate(OrderDurationType duration, Date purchaseDate){
        long time = purchaseDate.getTime();
        Date endDate = null;

        switch (duration){
            case YEAR:{
                long result = time + YEAR_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
            case HALF_YEAR:{
                long result = time + HALF_YEAR_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
            case MONTH:{
                long result = time + MONTH_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
        }

        return endDate;
    }

    public  BigDecimal calculatePrice(OrderDurationType duration, int isPersonalTrainerNeed){
        BigDecimal price = null;

        List<String> priceValues = extractPricesFromResources(duration,isPersonalTrainerNeed);
        if (!priceValues.isEmpty()){
            long priceValue = 0;

            for (String value : priceValues) {
                long currentValue = Long.parseLong(value);
                priceValue += currentValue;
            }

            price = new BigDecimal(priceValue);
        }

        return price;
    }

    private List<String> extractPricesFromResources(OrderDurationType duration, int isPersonalTrainerNeed){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(PRICES_FILE_NAME);
        List<String> priceValues = new ArrayList<>();

        switch (duration){
            case MONTH:{
                String durationPriceValue = resourceBundle.getString(MONTH_PRICE);
                priceValues.add(durationPriceValue);

                if (isPersonalTrainerNeed == 1) {
                    String personalTrainingsPriceValue = resourceBundle.getString(PERSONAL_TRAININGS_MONTH_PRICE);
                    priceValues.add(personalTrainingsPriceValue);
                }
                break;
            }case HALF_YEAR:{
                String durationPriceValue = resourceBundle.getString(HALF_YEAR_PRICE);
                priceValues.add(durationPriceValue);
                if (isPersonalTrainerNeed == 1) {
                    String personalTrainingsPriceValue = resourceBundle.getString(PERSONAL_TRAININGS_HALF_YEAR_PRICE);
                    priceValues.add(personalTrainingsPriceValue);
                }
                break;
            }case YEAR:{
                String durationPriceValue = resourceBundle.getString(YEAR_PRICE);
                priceValues.add(durationPriceValue);
                if (isPersonalTrainerNeed == 1) {
                    String personalTrainingsPriceValue = resourceBundle.getString(PERSONAL_TRAININGS_YEAR_PRICE);
                    priceValues.add(personalTrainingsPriceValue);
                }
                break;
            }
        }

        return priceValues;
    }

}
