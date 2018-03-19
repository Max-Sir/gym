package by.epam.gym.service;

import by.epam.gym.dao.user.UserDAO;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.DAOException;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.pool.ConnectionPool;
import by.epam.gym.utils.PasswordEncoder;

import java.sql.Connection;

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
     * @param login the user's login.
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

    public boolean checkUserLoginForUnique(String login) throws ServiceException {
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
