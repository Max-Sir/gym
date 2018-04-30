package by.epam.gym.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for user data validation.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.user.User
 */
public class UserDataValidator {

    private static final String LOGIN_PATTERN = "([a-zA-Z0-9_]+){6,}";
    private static final String PASSWORD_PATTERN = "([a-zA-Z0-9_]+){4,}";
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-я]+";
    private static final String SPACE_PATTERN = "\\s";

    /**
     * Check user registration data for errors.
     *
     * @param login     the user's login.
     * @param password  the user's password.
     * @param firstName the user's first name.
     * @param lastName  the user's last name.
     * @return result of validation.
     */
    public boolean checkData(String login, String password, String firstName, String lastName) {
        if (login == null || login.isEmpty()) {
            return false;
        }
        if (password == null || password.isEmpty()) {
            return false;
        }
        if (firstName == null || firstName.isEmpty()) {
            return false;
        }
        if (lastName == null || lastName.isEmpty()) {
            return false;
        }

        boolean isLoginValid = matchPattern(login, LOGIN_PATTERN);
        boolean isPasswordValid = matchPattern(password, PASSWORD_PATTERN);
        boolean isFirstNameValid = matchPattern(firstName, NAME_PATTERN);
        boolean isLastNameValid = matchPattern(lastName, NAME_PATTERN);

        return isLoginValid && isFirstNameValid && isPasswordValid && isLastNameValid;
    }

    /**
     * This method checks name to include first name and last name. Also checks it's syntax.
     *
     * @param name the name.
     * @return true if data is valid and false otherwise.
     */
    public boolean isNameFull(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        String pattern = NAME_PATTERN + SPACE_PATTERN + NAME_PATTERN;

        return matchPattern(name, pattern);
    }

    private boolean matchPattern(String data, String currentPattern) {
        Pattern pattern = Pattern.compile(currentPattern);
        Matcher matcher = pattern.matcher(data);

        return matcher.matches();
    }

}