package by.epam.gym.dao.user;

import by.epam.gym.dao.AbstractDAO;
import by.epam.gym.entities.user.User;
import by.epam.gym.entities.user.UserRole;
import by.epam.gym.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that provide access to the database and deal with User entity.
 *
 * @author Eugene Makarenko
 * @see AbstractDAO
 * @see User
 * @see UserDAO
 */
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {

    private static final String LOGIN_COLUMN_LABEL = "login";
    private static final String PASSWORD_COLUMN_LABEL = "password";
    private static final String ROLE_COLUMN_LABEL = "role";
    private static final String FIRST_NAME_COLUMN_LABEL = "first_name";
    private static final String LAST_NAME_COLUMN_LABEL = "last_name";

    private static final String TABLE_NAME = "users";

    private static final String INSERT_USER_SQL_QUERY = "INSERT INTO users (login, password, role, first_name, last_name) VALUES(?,?,?,?,?)";

    /**
     * Instantiates a new UserDAOImpl.
     *
     * @param connection the connection to database.
     */
    public UserDAOImpl(Connection connection) {
        super(connection);
    }

    /**
     * This method finds user in database by it's login and password.
     *
     * @param login    the user's login.
     * @param password the user's password.
     * @return the User object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public User findUserByLoginAndPassword(String login, String password) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN_AND_PASSWORD_SQL_QUERY)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = createEntity(resultSet);
            }

            return user;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * Check user's login for unique.
     *
     * @param login the user's login.
     * @return true if login is unique, else returns false.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public boolean checkLoginForUnique(String login) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_LOGIN_FOR_UNIQUE)) {
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * This method inserts user to database.
     *
     * @param entity the entity.
     * @return true if object was inserted successfully and false if didn't.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public boolean insert(User entity) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL_QUERY)) {
            UserRole userRole = entity.getUserRole();

            String login = entity.getLogin();
            String password = entity.getPassword();
            String roleValue = userRole.toString();
            String firstName = entity.getFirstName();
            String lastName = entity.getLastName();

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, roleValue);
            preparedStatement.setString(4, firstName);
            preparedStatement.setString(5, lastName);

            int queryResult = preparedStatement.executeUpdate();

            return queryResult > 0;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * Factory method for User entity creation.
     *
     * @param resultSet the result set of statement.
     * @return
     * @throws DAOException
     */
    @Override
    public User createEntity(ResultSet resultSet) throws DAOException {
        try {
            User user = new User();

            String userRoleValue = resultSet.getString(ROLE_COLUMN_LABEL);

            String login = resultSet.getString(LOGIN_COLUMN_LABEL);
            String password = resultSet.getString(PASSWORD_COLUMN_LABEL);
            UserRole userRole = UserRole.valueOf(userRoleValue);
            String firstName = resultSet.getString(FIRST_NAME_COLUMN_LABEL);
            String lastName = resultSet.getString(LAST_NAME_COLUMN_LABEL);

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
     * Method gets current table name.
     *
     * @return the table name;
     */
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
