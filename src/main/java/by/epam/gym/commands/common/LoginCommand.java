package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.user.User;
import by.epam.gym.entities.user.UserRole;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.UserService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.gym.utils.ConfigurationManager.*;
import static by.epam.gym.utils.MessageManager.ERROR_MESSAGE_PATH;
import static by.epam.gym.utils.MessageManager.LOGIN_ERROR_ATTRIBUTE;

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

    /**
     * Implementation of command that user use to log in.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);

        try {
            UserService userService = new UserService();
            User user = userService.login(login, password);
            if (user != null) {
                HttpSession currentSession = request.getSession();
                currentSession.setAttribute(USER_SESSION_ATTRIBUTE, user);
                pageUrl = userRoleRedirect(user);
            } else {
                String errorMessage = MessageManager.getProperty(ERROR_MESSAGE_PATH);
                request.setAttribute(LOGIN_ERROR_ATTRIBUTE, errorMessage);

                pageUrl = ConfigurationManager.getProperty(LOGIN_PAGE_PATH);
            }
            page.setRedirect(false);
        } catch (ServiceException exception) {
            LOGGER.error("Service exception detected. ", exception);
            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }

        page.setPageUrl(pageUrl);
        return page;
    }

    private String userRoleRedirect(User user) {
        UserRole userRole = user.getUserRole();

        switch (userRole) {
            case ADMIN: {
                return ConfigurationManager.getProperty(ADMIN_PAGE_PATH);
            }
            case CLIENT: {
                return ConfigurationManager.getProperty(CLIENT_PAGE_PATH);
            }
            case TRAINER: {
                return ConfigurationManager.getProperty(TRAINER_PAGE_PATH);
            }
            default: {
                return ConfigurationManager.getProperty(MAIN_PAGE_PATH);
            }
        }
    }
}
