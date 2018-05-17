package by.epam.gym.commands;

import static by.epam.gym.utils.MessageManager.NONE_MESSAGE_KEY;

/**
 * Class describes page url.
 *
 * @author Eugene Makarenko
 */
public class Page {

    /**
     * Common pages.
     */
    public static final String LOGIN_PAGE_PATH = "/jsp/common/login.jsp";
    public static final String MAIN_PAGE_PATH = "/jsp/common/main.jsp";
    public static final String ERROR_PAGE_PATH = "/jsp/common/error.jsp";
    public static final String REGISTER_PAGE_PATH = "/jsp/common/register.jsp";

    /**
     * Client's pages.
     */
    public static final String ADD_FEEDBACK_PAGE_PATH = "/jsp/client/add_feedback.jsp";
    public static final String PAY_ORDER_PAGE_PATH = "/jsp/client/pay_order.jsp";
    public static final String PREPARE_ORDER_PAGE_PATH = "/jsp/client/prepare_order.jsp";

    /**
     * Special pages.
     */

    public static final String SHOW_CLIENT_ORDERS_PAGE_PATH = "/jsp/special/show_client_orders.jsp";
    public static final String DESCRIBE_TRAINING_PROGRAM_PAGE_PATH = "/jsp/special/describe_training_program.jsp";
    public static final String EDIT_TRAINING_PROGRAM_PAGE_PATH = "/jsp/special/edit_training_program.jsp";

    /**
     * Admin's pages.
     */

    public static final String DESCRIBE_CLIENT_PAGE_PATH = "/jsp/admin/describe_client.jsp";
    public static final String SHOW_ALL_CLIENTS_PAGE_PATH = "/jsp/admin/show_all_clients.jsp";

    /**
     * Trainer pages.
     */
    public static final String CREATE_EXERCISE_PAGE_PATH = "/jsp/trainer/create_exercise.jsp";
    public static final String PERSONAL_CLIENTS_PAGE_PATH = "/jsp/trainer/personal_clients.jsp";
    public static final String CREATE_TRAINING_PROGRAM_PAGE_PATH = "/jsp/trainer/create_training_program.jsp";
    public static final String SHOW_EXERCISE = "/jsp/trainer/show_exercise.jsp";

    private String pageUrl;
    private boolean isRedirect;
    private String messageKey;

    /**
     * Instantiates a new Page.
     *
     * @param pageUrl    the page's url.
     * @param isRedirect boolean value of variable isRedirect;
     */
    public Page(String pageUrl, boolean isRedirect) {
        this.pageUrl = pageUrl;
        this.isRedirect = isRedirect;
        this.messageKey = NONE_MESSAGE_KEY;
    }

    /**
     * Instantiates a new Page.
     */
    public Page() {
    }

    /**
     * Instantiates a new Page.
     *
     * @param pageUrl    the page's url.
     * @param isRedirect boolean value of variable isRedirect;
     * @param messageKey the message key.
     */
    public Page(String pageUrl, boolean isRedirect, String messageKey) {
        this.pageUrl = pageUrl;
        this.isRedirect = isRedirect;
        this.messageKey = messageKey;
    }

    /**
     * Gets page url.
     *
     * @return the page's url.
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * Sets page url.
     *
     * @param pageUrl the page's url.
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /**
     * Get isRedirect value.
     *
     * @return boolean value of isRedirect variable.
     */
    public boolean isRedirect() {
        return isRedirect;
    }

    /**
     * Sets isRedirect value.
     *
     * @param redirect the boolean value.
     */
    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }

    /**
     * Gets message key.
     *
     * @return the message key.
     */
    public String getMessageKey() {
        return messageKey;
    }

    /**
     * Sets the message key.
     *
     * @param messageKey the message key.
     */
    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
