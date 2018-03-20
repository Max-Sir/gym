package by.epam.gym.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for checking user's input data in registration process.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.user.User
 * @see by.epam.gym.commands.common.RegisterCommand
 */
public class UserDataMatcher {

    public static final String LOGIN_PATTERN = "[A-Za-z0-9_]+";
    public static final String PASSWORD_PATTERN = "([A-Za-z0-9_]+){4,}";
    public static final String NAME_PATTERN = "^[A-Za-zА-Яа-я]+$";

    /**
     * This method checks user's data for matching with chosen pattern.
     *
     * @param data           the user's data.
     * @param currentPattern the pattern.
     * @return true if data is valid and false if not.
     */
    public static boolean match(String data, String currentPattern) {
        Pattern pattern = Pattern.compile(currentPattern);
        Matcher matcher = pattern.matcher(data);

        return matcher.matches();
    }

}
