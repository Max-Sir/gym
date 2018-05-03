package by.epam.gym.service;

import by.epam.gym.dao.ConnectionManager;
import by.epam.gym.dao.UserDAOImpl;
import by.epam.gym.entities.user.User;
import by.epam.gym.entities.user.UserRole;
import by.epam.gym.exceptions.DAOException;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.utils.PasswordEncoder;
import by.epam.gym.utils.UserDataValidator;

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

    private static final String NAME_SPLIT_SYMBOL = " ";

    private static final int FIRST_NAME_INDEX = 0;
    private static final int LAST_NAME_INDEX = 1;

    /**
     * The method returns authorized user.
     *
     * @param login    the user's login.
     * @param password the user's password.
     * @return the User.
     * @throws ServiceException object if execution of method is failed.
     */
    public User login(String login, String password) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            UserDAOImpl userDAO = new UserDAOImpl(connectionManager.getConnection());
            password = PasswordEncoder.encode(password);

            return userDAO.selectUserByLoginAndPassword(login, password);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during login operation.", exception);
        }
    }

    /**
     * The method registers user into data base.
     *
     * @param login     the user's login.
     * @param password  the user's password.
     * @param firstName the user's first name.
     * @param lastName  the user's last name.
     * @return true if operation was made successful and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean register(String login, String password, String firstName, String lastName) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            UserDAOImpl userDAO = new UserDAOImpl(connectionManager.getConnection());

            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            UserRole userRole = UserRole.CLIENT;
            user.setUserRole(userRole);
            user.setFirstName(firstName);
            user.setLastName(lastName);

            return userDAO.insert(user);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during register operation.", exception);
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
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            UserDAOImpl userDAO = new UserDAOImpl(connectionManager.getConnection());

            return userDAO.checkLoginForUnique(login);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during check user login for unique operation.", exception);
        }
    }

    /**
     * This method finds user by first name and last name.
     *
     * @param name the user's first name.
     * @return List of users.
     * @throws ServiceException object if execution of method is failed.
     */
    public List<User> findClientByName(String name) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            UserDataValidator userDataValidator = new UserDataValidator();
            UserDAOImpl userDAO = new UserDAOImpl(connectionManager.getConnection());
            boolean isNameFull = userDataValidator.isNameFull(name);
            if (isNameFull) {
                String[] names = name.split(NAME_SPLIT_SYMBOL);
                String firstName = names[FIRST_NAME_INDEX];
                String lastName = names[LAST_NAME_INDEX];

                return userDAO.selectClientByFullName(firstName, lastName);
            } else {
                return userDAO.selectClientByNamePart(name);
            }
        } catch (DAOException exception) {
            throw new ServiceException("Exception during find client by name operation.", exception);
        }
    }

    /**
     * This method finds all clients in database.
     *
     * @return Map of clients and number of records.
     * @throws ServiceException object if execution of method is failed.
     */
    public Map<List<User>, Integer> findAllClientsByPages(int offSet, int numberOfRecords) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            UserDAOImpl userDAO = new UserDAOImpl(connectionManager.getConnection());
            ;
            Map<List<User>, Integer> clients = new HashMap<>();

            List<User> findClient = userDAO.selectAllClientsByFoundRows(offSet, numberOfRecords);
            Integer countOfRecords = userDAO.getNumberOfRecords();

            clients.put(findClient, countOfRecords);

            return clients;
        } catch (DAOException exception) {
            throw new ServiceException("Exception during find all clients by pages operation.", exception);
        }
    }

    /**
     * This method finds all clients of current trainer and their training programs id .
     *
     * @param trainerId the trainer id.
     * @return List with results.
     * @throws ServiceException object if execution of method is failed.
     */
    public List<User> findPersonalClients(int trainerId) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            UserDAOImpl userDAO = new UserDAOImpl(connectionManager.getConnection());

            return userDAO.selectPersonalClients(trainerId);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during find personal clients operation.", exception);
        }
    }

    /**
     * This method finds clients for training program creation.
     *
     * @return Map with id and name.
     * @throws ServiceException object if execution of method is failed.
     */
    public Map<Integer, String> findClientsForTrainingProgramCreation() throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            UserDAOImpl userDAO = new UserDAOImpl(connectionManager.getConnection());

            return userDAO.selectClientIdAndNameForTrainingProgramCreation();
        } catch (DAOException exception) {
            throw new ServiceException("Exception during find clients id and name operation.", exception);
        }
    }

}
