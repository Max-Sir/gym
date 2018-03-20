package by.epam.gym.service;

import by.epam.gym.dao.user.UserDAO;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.DAOException;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.pool.ConnectionPool;
import by.epam.gym.utils.MessageManager;
import by.epam.gym.utils.PasswordEncoder;
import by.epam.gym.utils.UserDataMatcher;

import java.sql.Connection;
import java.util.HashMap;

import static by.epam.gym.utils.MessageManager.*;

/**
 * Service class for User entity.
 *
 * @author Eugene Makarenko
 * @see User
 * @see UserDAO
 * @see ServiceException
 */
public class UserService {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    /**
     * The method returns authorized user.
     *
     * @param login    the user's login.
     * @param password the user's password.
     * @return the User.
     * @throws ServiceException object if execution of method is failed.
     */
    public User login(String login, String password) throws ServiceException {
        password = PasswordEncoder.encode(password);

        Connection connection = CONNECTION_POOL.getConnection();
        UserDAO userDAO = new UserDAO(connection);

        try {
            User user = userDAO.findUserByLoginAndPassword(login, password);

            return user;
        } catch (DAOException exception) {
            throw new ServiceException("DAO exception detected. " + exception);
        } finally {
            CONNECTION_POOL.returnConnection(connection);
        }
    }

    /**
     * The method registers user into data base.
     *
     * @param user the created user.
     * @return true if registration was successful and false if not.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean register(User user) throws ServiceException {
        Connection connection = CONNECTION_POOL.getConnection();
        UserDAO userDAO = new UserDAO(connection);

        try {
            return userDAO.insert(user);
        } catch (DAOException exception) {
            throw new ServiceException("DAO exception detected. " + exception);
        } finally {
            CONNECTION_POOL.returnConnection(connection);
        }
    }

    /**
     * Check user registration data for errors.
     *
     * @param login     the user's login.
     * @param password  the user's password.
     * @param firstName the user's first name.
     * @param lastName  the user's last name.
     * @return HashMap with errors, if they exist.
     * @throws ServiceException object if execution of method is failed.
     */
    public HashMap<String, String> checkUserRegisterData(String login, String password, String firstName, String lastName) throws ServiceException {
        HashMap<String, String> messages = new HashMap<>();

        boolean isLoginNotUnique = checkUserLoginForUnique(login);
        boolean isLoginValid = UserDataMatcher.match(login, UserDataMatcher.LOGIN_PATTERN);
        boolean isPasswordValid = UserDataMatcher.match(password, UserDataMatcher.PASSWORD_PATTERN);
        boolean isFirstNameValid = UserDataMatcher.match(firstName, UserDataMatcher.NAME_PATTERN);
        boolean isLastNameValid = UserDataMatcher.match(lastName, UserDataMatcher.NAME_PATTERN);

        if (!isLoginValid) {
            String errorMessage = MessageManager.getProperty(MessageManager.LOGIN_ERROR_MESSAGE_PATH);
            messages.put(LOGIN_ERROR_ATTRIBUTE, errorMessage);
        } else {
            if (isLoginNotUnique) {
                String errorMessage = MessageManager.getProperty(MessageManager.LOGIN_NOT_UNIQUE_ERROR_MESSAGE_PATH);
                messages.put(LOGIN_ERROR_ATTRIBUTE, errorMessage);
            }
        }
        if (!isPasswordValid) {
            String errorMessage = MessageManager.getProperty(MessageManager.PASSWORD_ERROR_MESSAGE_PATH);
            messages.put(PASS_ERROR_ATTRIBUTE, errorMessage);
        }
        if (!isFirstNameValid) {
            String errorMessage = MessageManager.getProperty(MessageManager.NAME_ERROR_MESSAGE_PATH);
            messages.put(FIRST_NAME_ERROR_ATTRIBUTE, errorMessage);
        }
        if (!isLastNameValid) {
            String errorMessage = MessageManager.getProperty(MessageManager.NAME_ERROR_MESSAGE_PATH);
            messages.put(LAST_NAME_ERROR_ATTRIBUTE, errorMessage);
        }

        return messages;
    }

    private boolean checkUserLoginForUnique(String login) throws ServiceException {
        Connection connection = CONNECTION_POOL.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {

            return userDAO.checkLoginForUnique(login);
        } catch (DAOException exception) {
            throw new ServiceException("DAO exception detected. " + exception);
        } finally {
            CONNECTION_POOL.returnConnection(connection);
        }
    }
}
