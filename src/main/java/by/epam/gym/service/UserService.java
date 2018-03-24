package by.epam.gym.service;

import by.epam.gym.dao.UserDAOImpl;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.utils.PasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * This method finds user by first name and last name.
     *
     * @param firstName the user's first name.
     * @param lastName the user's last name.
     * @return List of users.
     * @throws ServiceException object if execution of method is failed.
     */
    public List<User> findClientByName(String firstName, String lastName) throws ServiceException {
        try(ConnectionManager<UserDAOImpl> connectionManager = new ConnectionManager<>(UserDAOImpl.class)) {
            UserDAOImpl userDAO = connectionManager.createDAO();

            return userDAO.findClientByName(firstName,lastName);
        }  catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * This method finds all clients in database.
     *
     * @return Map of clients and number of records.
     * @throws ServiceException object if execution of method is failed.
     */
    public Map<List<User>, Integer> findAllClientsByPages(int offSet, int numberOfRecords) throws ServiceException {
        try(ConnectionManager<UserDAOImpl> connectionManager = new ConnectionManager<>(UserDAOImpl.class)) {
            UserDAOImpl userDAO = connectionManager.createDAO();
            Map<List<User>, Integer> clients = new HashMap<>();

            List<User> findClient = userDAO.findAllClientsByPages(offSet, numberOfRecords);
            Integer countOfRecords = userDAO.getNumberOfRecords();

            clients.put(findClient,countOfRecords);

            return  clients;
        } catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }
}
