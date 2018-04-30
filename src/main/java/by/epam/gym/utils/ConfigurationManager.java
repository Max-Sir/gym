package by.epam.gym.utils;

import java.util.ResourceBundle;

/**
 * Util class for getting JSP page.
 *
 * @author Eugene Makarenko
 * @see ResourceBundle
 */
public class ConfigurationManager {

    /**
     * Common pages.
     */
    public static final String LOGIN_PAGE_PATH = "path.page.login";
    public static final String MAIN_PAGE_PATH = "path.page.main";
    public static final String ERROR_PAGE_PATH = "path.page.error";
    public static final String REGISTER_PAGE_PATH = "path.page.register";
    public static final String REGISTRATION_SUCCESSFUL_PAGE_PATH = "path.page.registration_successful";

    /**
     * Special pages
     */
    public static final String SHOW_CLIENT_ORDERS_PAGE_PATH = "path.page.show_client_orders";
    public static final String DESCRIBE_TRAINING_PROGRAM_PAGE_PATH = "path.page.describe_training_program";
    public static final String EDIT_TRAINING_PROGRAM_PAGE_PATH = "path.page.edit_training_program";

    /**
     * Admin pages.
     */
    public static final String DESCRIBE_CLIENT_PAGE_PATH = "path.page.describe_client";
    public static final String SHOW_ALL_CLIENTS_PAGE_PATH = "path.page.show_all_clients";

    /**
     * Client pages.
     */
    public static final String ADD_FEEDBACK_PAGE_PATH = "path.page.add_feedback";
    public static final String PAY_ORDER_PAGE_PATH = "path.page.pay_order";
    public static final String RESULT_PAGE_PATH = "path.page.result";

    /**
     * Trainer pages.
     */
    public static final String CREATE_EXERCISE_PAGE_PATH = "path.page.create_exercise";
    public static final String PERSONAL_CLIENTS_PAGE_PATH = "path.page.personal_clients";
    public static final String CREATE_TRAINING_PROGRAM_PAGE_PATH = "path.page.create_training_program";
    public static final String SHOW_EXERCISE = "path.page.show_exercise";

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
