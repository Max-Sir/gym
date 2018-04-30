package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.UserService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.PasswordEncoder;
import by.epam.gym.utils.UserDataValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.gym.utils.MessageManager.*;

/**
 * Command for user registration.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see by.epam.gym.entities.user.User
 */
public class RegisterCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);

    /**
     * Implementation of command that user register.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {

        try {
            String login = request.getParameter(LOGIN_PARAMETER);
            String password = request.getParameter(PASSWORD_PARAMETER);
            String firstName = request.getParameter(FIRST_NAME_PARAMETER);
            String lastName = request.getParameter(LAST_NAME_PARAMETER);

            UserService userService = new UserService();
            boolean isLoginNotUnique = userService.checkUserLoginForUnique(login);
            if (isLoginNotUnique) {
                return new Page(Page.REGISTER_PAGE_PATH, false, LOGIN_NOT_AVAILABLE_MESSAGE_KEY);
            }

            UserDataValidator userDataValidator = new UserDataValidator();
            boolean isUserDataValid = userDataValidator.checkData(login, password, firstName, lastName);
            if (!isUserDataValid) {
                return new Page(Page.REGISTER_PAGE_PATH, false, INVALID_INPUT_DATA_MESSAGE_KEY);
            }

            password = PasswordEncoder.encode(password);
            boolean isOperationSuccessful = userService.register(login, password, firstName, lastName);
            if (!isOperationSuccessful) {
                LOGGER.info(String.format("User: login - %s, had some problems during registration.", login));
                return new Page(Page.REGISTER_PAGE_PATH, false, REGISTRATION_FAILED_MESSAGE_KEY);
            }

            HttpSession session = request.getSession();
            session.setAttribute(IS_RECORD_INSERTED, true);

            LOGGER.info(String.format("User: login - %s, registered successful.", login));
            return new Page(Page.LOGIN_PAGE_PATH, false, REGISTRATION_SUCCESSFUL_MESSAGE_KEY);

        } catch (ServiceException exception) {
            LOGGER.error(String.format("Service exception detected in command - %s. ", getClass().getSimpleName()), exception);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }
}

