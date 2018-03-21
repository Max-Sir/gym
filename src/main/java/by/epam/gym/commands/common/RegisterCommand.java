package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.user.User;
import by.epam.gym.entities.user.UserRole;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.UserService;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;
import by.epam.gym.utils.PasswordEncoder;
import by.epam.gym.utils.UserDataValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.REGISTER_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.LOGIN_NOT_UNIQUE_ERROR_MESSAGE_PATH;
import static by.epam.gym.utils.MessageManager.RESULT_ATTRIBUTE;

/**
 * Command for user registration.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see by.epam.gym.entities.user.User
 */
public class RegisterCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String FIRST_NAME_PARAMETER = "first_name";
    private static final String LAST_NAME_PARAMETER = "last_name";

    /**
     * Implementation of command that user register.
     *
     * @param request HttpServletRequest object.
     * @return registered page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        UserService userService = new UserService();

        try {
            String login = request.getParameter(LOGIN_PARAMETER);
            String password = request.getParameter(PASSWORD_PARAMETER);
            String firstName = request.getParameter(FIRST_NAME_PARAMETER);
            String lastName = request.getParameter(LAST_NAME_PARAMETER);
            UserRole userRole = UserRole.CLIENT;

            boolean isLoginNotUnique = userService.checkUserLoginForUnique(login);

            if (isLoginNotUnique) {
                page = ConfigurationManager.getProperty(REGISTER_PAGE_PATH);
                request.setAttribute(MessageManager.LOGIN_ERROR_ATTRIBUTE,MessageManager.getProperty(LOGIN_NOT_UNIQUE_ERROR_MESSAGE_PATH));
            } else {

                UserDataValidator userDataValidator = new UserDataValidator();
                boolean isUserDataValid = userDataValidator.checkData(login,password,firstName,lastName);

                if (isUserDataValid){
                    password = PasswordEncoder.encode(password);

                    User user = new User();
                    user.setLogin(login);
                    user.setPassword(password);
                    user.setUserRole(userRole);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);

                    if (userService.register(user)) {
                        page = ConfigurationManager.getProperty(REGISTER_PAGE_PATH);
                        request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(MessageManager.REGISTRATION_SUCCESS_MESSAGE_PATH));
                    } else {
                        page = ConfigurationManager.getProperty(REGISTER_PAGE_PATH);
                        request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(MessageManager.REGISTRATION_FAILED_MESSAGE_PATH));
                    }
                } else {
                    page = ConfigurationManager.getProperty(REGISTER_PAGE_PATH);
                    request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(MessageManager.DATA_NOT_VALID_MESSAGE_PATH));
                }

            }

        } catch (ServiceException exception) {
            LOGGER.error("Service exception detected. ", exception);
            page = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
        }

        return page;
    }
}
