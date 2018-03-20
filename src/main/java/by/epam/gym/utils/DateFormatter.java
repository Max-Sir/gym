package by.epam.gym.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util class for formatting user's birth date.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.user.User
 */
public class DateFormatter {

    private static final String BIRTH_DATE_PATTERN = "yyyy-MM-dd";

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(BIRTH_DATE_PATTERN);

    /**
     * Format date to string.
     *
     * @param date the user's date.
     * @return string value of date.
     */
    public static String formatDateToString(Date date) {
        String result = DATE_FORMATTER.format(date);

        return result;
    }

    /**
     * Format string to date.
     *
     * @param date the user's date.
     * @return Date value of string.
     * @throws ParseException if date format is incorrect.
     */
    public static Date formatStringToDate(String date) throws ParseException {
        Date birthDate = DATE_FORMATTER.parse(date);

        return birthDate;
    }
}
