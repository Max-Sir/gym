package by.epam.gym.dao;

import by.epam.gym.entities.user.User;
import by.epam.gym.entities.user.UserRole;
import by.epam.gym.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that provide access to the database and deal with User entity.
 *
 * @author Eugene Makarenko
 * @see AbstractDAOImpl
 * @see User
 */
public class UserDAOImpl extends AbstractDAOImpl<User> {

    private static final String LOGIN_COLUMN_LABEL = "login";
    private static final String PASSWORD_COLUMN_LABEL = "password";
    private static final String ROLE_COLUMN_LABEL = "role";
    private static final String FIRST_NAME_COLUMN_LABEL = "first_name";
    private static final String LAST_NAME_COLUMN_LABEL = "last_name";

    private static final int FIRST_COLUMN_INDEX = 1;

    private static final String USERS_RESOURCES_FILE_NAME = "users";

    private int numberOfRecords;

    /**
     * Instantiates a new UserDAOImpl.
     *
     * @param connection the connection to database.
     */
    public UserDAOImpl(Connection connection) {
        super(connection,USERS_RESOURCES_FILE_NAME);
    }

    /**
     * This method finds user in database by it's login and password.
     *
     * @param login    the user's login.
     * @param password the user's password.
     * @return the User object.
     * @throws DAOException object if execution of query is failed.
     */
    public User findUserByLoginAndPassword(String login, String password) throws DAOException {
        String sqlQuery = resourceBundle.getString("query.find_by_login_and_password");
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = buildEntity(resultSet);
            }

            return user;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * This method checks user's login for unique.
     *
     * @param login the user's login.
     * @return true if login is unique, else returns false.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean checkLoginForUnique(String login) throws DAOException {
        String sqlQuery = resourceBundle.getString("query.check_login_for_unique");
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * This method finds user by his name.
     *
     * @param firstName the user's name.
     * @param lastName the user's name.
     * @return List of found users.
     * @throws DAOException object if execution of query is failed.
     */
    public List<User> findClientByName(String firstName, String lastName) throws DAOException {
        String sqlQuery = resourceBundle.getString("query.find_client_by_name");
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> findUsers = new ArrayList<>();
            while (resultSet.next()){
                User user = buildEntity(resultSet);

                findUsers.add(user);
            }

            return findUsers;
        }catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * This method finds all clients in database.
     *
     * @return List of clients.
     * @throws DAOException object if execution of query is failed.
     */
    public List<User> findAllClientsByPages(int offSet, int numberOfRecords) throws DAOException {
        String sqlQueryFirst = resourceBundle.getString("query.find_all_clients_by_pages");
        String sqlQuerySecond = resourceBundle.getString("query.select_found_rows");
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQueryFirst + offSet + ", " + numberOfRecords);

            List<User> findUsers = new ArrayList<>();
            while (resultSet.next()){
                User user = buildEntity(resultSet);

                findUsers.add(user);
            }

            resultSet = statement.executeQuery(sqlQuerySecond);
            if (resultSet.next()){
                this.numberOfRecords = resultSet.getInt(FIRST_COLUMN_INDEX);
            }

            return findUsers;
        }catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * This method builds User object from ResultSet object.
     *
     * @param resultSet the result set of statement.
     * @return The User object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public User buildEntity(ResultSet resultSet) throws DAOException {
        try {
            User user = new User();

            String userRoleValue = resultSet.getString(ROLE_COLUMN_LABEL);

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            String login = resultSet.getString(LOGIN_COLUMN_LABEL);
            String password = resultSet.getString(PASSWORD_COLUMN_LABEL);
            UserRole userRole = UserRole.valueOf(userRoleValue);
            String firstName = resultSet.getString(FIRST_NAME_COLUMN_LABEL);
            String lastName = resultSet.getString(LAST_NAME_COLUMN_LABEL);

            user.setId(id);
            user.setLogin(login);
            user.setPassword(password);
            user.setUserRole(userRole);
            user.setFirstName(firstName);
            user.setLastName(lastName);

            return user;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * Gets number of records.
     *
     * @return the number of records.
     */
    public int getNumberOfRecords() {
        return numberOfRecords;
    }
}
