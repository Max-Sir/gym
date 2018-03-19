package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.user.User;
import by.epam.gym.entities.user.UserRole;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.UserService;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;
import by.epam.gym.utils.PasswordEncoder;
import by.epam.gym.utils.UserDataMatcher;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.LOGIN_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.REGISTER_PAGE_PATH;

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

    private static final String LOGIN_ERROR_ATTRIBUTE = "loginError";
    private static final String PASS_ERROR_ATTRIBUTE = "passError";
    private static final String FIRST_NAME_ERROR_ATTRIBUTE = "firstNameError";
    private static final String LAST_NAME_ERROR_ATTRIBUTE = "lastNameError";
    private static final String RESULT_ATTRIBUTE = "result";

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

            HashMap<String, String> errors = errorMessagesInRegistration(userService,login,password,firstName,lastName);
            if (errors.size() == 0){
                password = PasswordEncoder.encode(password);

                User user = new User();
                user.setLogin(login);
                user.setPassword(password);
                user.setUserRole(userRole);
                user.setFirstName(firstName);
                user.setLastName(lastName);

                if (userService.register(user)){
                    page = ConfigurationManager.getProperty(REGISTER_PAGE_PATH);
                    request.setAttribute(RESULT_ATTRIBUTE,MessageManager.getProperty(MessageManager.REGISTRATION_SUCCESS_MESSAGE_PATH));
                } else {
                    page = ConfigurationManager.getProperty(REGISTER_PAGE_PATH);
                    request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(MessageManager.REGISTRATION_FAILED_MESSAGE_PATH));
                }
            } else {
                page = ConfigurationManager.getProperty(REGISTER_PAGE_PATH);
                Set<Map.Entry<String, String>> errorsSet = errors.entrySet();
                for (Map.Entry<String, String> error : errorsSet) {
                    String attribute = error.getKey();
                    String errorMessage = error.getValue();

                    request.setAttribute(attribute,errorMessage);
                }
            }

        } catch (ServiceException exception) {
            LOGGER.error("Service exception detected. ", exception);
            page = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
        }

        return page;
    }

    private HashMap<String, String> errorMessagesInRegistration(UserService userService, String login, String password, String firstName, String lastName) throws ServiceException {
        HashMap<String, String> messages = new HashMap<>();

        boolean isLoginUnique = userService.checkUserLoginForUnique(login);
        boolean isLoginValid = UserDataMatcher.match(login, UserDataMatcher.LOGIN_PATTERN);
        boolean isPasswordValid = UserDataMatcher.match(password,UserDataMatcher.PASSWORD_PATTERN);
        boolean isFirstNameValid = UserDataMatcher.match(firstName,UserDataMatcher.NAME_PATTERN);
        boolean isLastNameValid = UserDataMatcher.match(lastName,UserDataMatcher.NAME_PATTERN);

        if (!isLoginValid){
            String errorMessage = MessageManager.getProperty(MessageManager.LOGIN_ERROR_MESSAGE_PATH);
            messages.put(LOGIN_ERROR_ATTRIBUTE,errorMessage);
        } else {
            if (isLoginUnique){
                String errorMessage = MessageManager.getProperty(MessageManager.LOGIN_NOT_UNIQUE_ERROR_MESSAGE_PATH);
                messages.put(LOGIN_ERROR_ATTRIBUTE,errorMessage);
            }
        }
        if (!isPasswordValid){
            String errorMessage = MessageManager.getProperty(MessageManager.PASSWORD_ERROR_MESSAGE_PATH);
            messages.put(PASS_ERROR_ATTRIBUTE,errorMessage);
        }
        if (!isFirstNameValid){
            String errorMessage = MessageManager.getProperty(MessageManager.NAME_ERROR_MESSAGE_PATH);
            messages.put(FIRST_NAME_ERROR_ATTRIBUTE,errorMessage);
        }
        if (!isLastNameValid){
            String errorMessage = MessageManager.getProperty(MessageManager.NAME_ERROR_MESSAGE_PATH);
            messages.put(LAST_NAME_ERROR_ATTRIBUTE,errorMessage);
        }

        return messages;
    }
}
