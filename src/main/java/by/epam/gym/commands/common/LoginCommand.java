package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.user.User;
import by.epam.gym.entities.user.UserRole;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.UserService;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;
import by.epam.gym.utils.PasswordEncoder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.gym.utils.ConfigurationManager.*;
import static by.epam.gym.utils.MessageManager.ERROR_MESSAGE_PATH;

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

    /**
     * Implementation of command that user use to log in.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);

        try {
            UserService userService = new UserService();
            User user = userService.login(login, password);
            if (user != null) {
                HttpSession currentSession = request.getSession();
                currentSession.setAttribute(USER_SESSION_ATTRIBUTE, user);

                page = userRoleRedirect(user);
            } else {
                String errorMessage = MessageManager.getProperty(ERROR_MESSAGE_PATH);
                request.setAttribute(ERROR_LOGIN_ATTRIBUTE, errorMessage);

                page = ConfigurationManager.getProperty(LOGIN_PAGE_PATH);
            }

        } catch (ServiceException exception) {
            LOGGER.error("Service exception detected. ", exception);
            page = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
        }

        return page;
    }

    private String userRoleRedirect(User user){
        UserRole userRole = user.getUserRole();

        switch (userRole){
            case ADMIN: {
                return ConfigurationManager.getProperty(ADMIN_PAGE_PATH);
            }
            case CLIENT:{
                return ConfigurationManager.getProperty(CLIENT_PAGE_PATH);
            }
            case TRAINER:{
                return ConfigurationManager.getProperty(TRAINER_PAGE_PATH);
            }
            default:{
                return ConfigurationManager.getProperty(MAIN_PAGE_PATH);
            }
        }
    }
}
