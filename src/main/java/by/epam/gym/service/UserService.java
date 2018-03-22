package by.epam.gym.service;

import by.epam.gym.dao.UserDAOImpl;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.utils.PasswordEncoder;

/**
 * Service class for User entity.
 *
 * @author Eugene Makarenko
 * @see User
 * @see UserDAOImpl
 * @see ServiceException
 * @see ConnectionManager
 */
public class UserService {

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

        try(ConnectionManager<UserDAOImpl> connectionManager = new ConnectionManager<>(UserDAOImpl.class)) {
            UserDAOImpl userDAO = connectionManager.createDAO();

            return userDAO.findUserByLoginAndPassword(login, password);
        } catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * The method registers user into data base.
     *
     * @param user the created user.
     * @throws ServiceException object if execution of method is failed.
     */
    public void register(User user) throws ServiceException {

        try(ConnectionManager<UserDAOImpl> connectionManager = new ConnectionManager<>(UserDAOImpl.class)) {
            UserDAOImpl userDAO = connectionManager.createDAO();
            userDAO.insert(user);
        }  catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * The method checks user login for unique value during registration.
     *
     * @param login the user's login.
     * @return true if login is unique and false if not.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean checkUserLoginForUnique(String login) throws ServiceException {

        try(ConnectionManager<UserDAOImpl> connectionManager = new ConnectionManager<>(UserDAOImpl.class)) {
            UserDAOImpl userDAO = connectionManager.createDAO();

            return userDAO.checkLoginForUnique(login);
        }  catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }
}
