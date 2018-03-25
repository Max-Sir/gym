package by.epam.gym.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Util class for changing JSP page's language.
 *
 * @author Eugene Makarenko
 * @see ResourceBundle
 * @see Locale
 */
public class MessageManager {

    public static final String LOGIN_ERROR_ATTRIBUTE = "loginError";
    public static final String RESULT_ATTRIBUTE = "result";

    public static final String ERROR_MESSAGE_PATH = "message.loginError";
    public static final String WRONG_ACTION_MESSAGE_PATH = "message.wrongAction";
    public static final String NULL_PAGE_MESSAGE_PATH = "message.nullPage";
    public static final String LOGIN_NOT_UNIQUE_ERROR_MESSAGE_PATH = "message.notUniqueLogin";
    public static final String REGISTRATION_SUCCESS_MESSAGE_PATH = "message.regSuccessful";
    public static final String REGISTRATION_FAILED_MESSAGE_PATH = "message.regError";
    public static final String DATA_NOT_VALID_MESSAGE_PATH = "message.notValid";
    public static final String USER_DID_NOT_FIND_MESSAGE_PATH = "message.userNotFound";
    public static final String EXERCISE_ADDED_SUCCESSFULLY = "message.exerciseAdded";

    private static final Locale DEFAULT_LOCALE = new Locale("", "");
    private static final String RESOURCE_FILE_NAME = "messages";

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME, DEFAULT_LOCALE);

    private MessageManager() {
    }

    /**
     * Gets property from resource file.
     *
     * @param key the key of property.
     * @return the property.
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * Change language of jsp page.
     *
     * @param locale the locale.
     */
    public static void changeLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME, locale);
    }

}
