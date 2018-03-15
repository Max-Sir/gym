package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.services.LoginService;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Command for user log in.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 */
public class LoginCommand implements ActionCommand {

    private final static Logger LOGGER = Logger.getLogger(LoginCommand.class);

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    private static final String USER_SESSION_ATTRIBUTE = "user";
    private static final String ERROR_LOGIN_ATTRIBUTE = "errorLoginPassMessage";

    private static final String LOGIN_PAGE_PATH = "path.page.login";
    private static final String MAIN_PAGE_PATH = "path.page.main";

    private static final String ERROR_MESSAGE_PATH = "message.loginError";

    /**
     * Implementation of command that user use to log in.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     */
    public String execute(HttpServletRequest request) {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);

        LoginService loginService = new LoginService();
        try {
            User user = loginService.getAuthorizedUser(login, password);

            if (user != null) {
                HttpSession currentSession = request.getSession();
                currentSession.setAttribute(USER_SESSION_ATTRIBUTE, user);

                page = ConfigurationManager.getProperty(MAIN_PAGE_PATH);
            } else {
                String errorMessage = MessageManager.getProperty(ERROR_MESSAGE_PATH);
                request.setAttribute(ERROR_LOGIN_ATTRIBUTE, errorMessage);

                page = ConfigurationManager.getProperty(LOGIN_PAGE_PATH);
            }

        } catch (ServiceException exception) {
            LOGGER.error("Service exception detected.", exception);
        }

        return page;
    }
}
