package by.epam.gym.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for user data validation.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.user.User
 * @see by.epam.gym.commands.common.RegisterCommand
 */
public class UserDataValidator {

    private static final String LOGIN_PATTERN = "[a-zA-Z0-9_]+";
    private static final String PASSWORD_PATTERN = "[a-zA-Z0-9_]+{4,}";
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-я]+";

    /**
     * Check user registration data for errors.
     *
     * @param login     the user's login.
     * @param password  the user's password.
     * @param firstName the user's first name.
     * @param lastName  the user's last name.
     * @return result of validation.
     */
    public boolean checkData(String login, String password, String firstName, String lastName){

        boolean isLoginValid = matchPattern(login, LOGIN_PATTERN);
        boolean isPasswordValid = matchPattern(password, PASSWORD_PATTERN);
        boolean isFirstNameValid = matchPattern(firstName, NAME_PATTERN);
        boolean isLastNameValid = matchPattern(lastName, NAME_PATTERN);

        return isLoginValid && isFirstNameValid && isPasswordValid && isLastNameValid;
    }

    private boolean matchPattern(String data, String currentPattern) {
        Pattern pattern = Pattern.compile(currentPattern);
        Matcher matcher = pattern.matcher(data);

        return matcher.matches();
    }

}
