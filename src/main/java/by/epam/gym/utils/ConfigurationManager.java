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
    public static final String ADMIN_PAGE_PATH = "path.page.admin";
    public static final String CLIENT_PAGE_PATH = "path.page.client";
    public static final String TRAINER_PAGE_PATH = "path.page.trainer";
    public static final String ERROR_PAGE_PATH = "path.page.error";
    public static final String REGISTER_PAGE_PATH = "path.page.register";
    public static final String REGISTRATION_SUCCESSFUL_PAGE_PATH = "path.page.registration_successful";
    public static final String FIND_CLIENT_BY_NAME_PAGE_PATH = "path.page.find_client";
    public static final String SHOW_ALL_CLIENTS_PAGE_PATH = "path.page.show_all_clients";
    public static final String ADD_EXERCISE_PAGE_PATH = "path.page.add_exercise";
    public static final String PERSONAL_CLIENTS_PAGE_PATH = "path.page.personal_clients";

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
