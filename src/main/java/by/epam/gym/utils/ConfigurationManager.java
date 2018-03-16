package by.epam.gym.utils;

import java.util.ResourceBundle;

/**
 * Util class for changing JSP page.
 *
 * @author Eugene Makarenko
 * @see ResourceBundle
 */
public class ConfigurationManager {

    public static final String LOGIN_PAGE_PATH = "path.page.login";
    public static final String MAIN_PAGE_PATH = "path.page.main";
    public static final String INDEX_PAGE_PATH = "path.page.index";

    private static final String RESOURCE_FILE_NAME = "config";

    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(RESOURCE_FILE_NAME);

    private ConfigurationManager() {
    }

    /**
     * Gets property from resource file.
     *
     * @param key the key of property.
     * @return the property.
     */
    public static String getProperty(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }

}
