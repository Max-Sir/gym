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

    public static final String ERROR_MESSAGE_PATH = "message.loginError";

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
