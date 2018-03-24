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

    private static final String ID_COLUMN_LABEL = "id";
    private static final String LOGIN_COLUMN_LABEL = "login";
    private static final String PASSWORD_COLUMN_LABEL = "password";
    private static final String ROLE_COLUMN_LABEL = "role";
    private static final String FIRST_NAME_COLUMN_LABEL = "first_name";
    private static final String LAST_NAME_COLUMN_LABEL = "last_name";

    private static final int FIRST_COLUMN_INDEX = 1;

    private static final String TABLE_NAME = "users";

    private static final String FIND_BY_LOGIN_AND_PASSWORD_SQL_QUERY = "SELECT * FROM users WHERE login=? AND password=?";
    private static final String CHECK_LOGIN_FOR_UNIQUE = "SELECT * FROM users WHERE login=?";
    private static final String INSERT_USER_SQL_QUERY = "INSERT INTO users (login, password, role, first_name, last_name) VALUES(?,?,?,?,?)";
    private static final String FIND_USER_BY_NAME_SQL_QUERY = "SELECT * FROM users WHERE first_name=? AND last_name=?";
    private static final String FIND_ALL_CLIENTS_BY_PAGES_SQL_QUERY = "SELECT SQL_CALC_FOUND_ROWS * FROM users WHERE role='CLIENT' LIMIT ";
    private static final String SQL_SELECT_FOUND_ROWS = "SELECT FOUND_ROWS()";

    private int numberOfRecords;

    /**
     * Instantiates a new UserDAOImpl.
     *
     * @param connection the connection to database.
     */
    public UserDAOImpl(Connection connection) {
        super(connection,"users");
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN_AND_PASSWORD_SQL_QUERY)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_LOGIN_FOR_UNIQUE)) {
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
    public List<User> findUserByName(String firstName, String lastName) throws DAOException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_NAME_SQL_QUERY)) {
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
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_CLIENTS_BY_PAGES_SQL_QUERY + offSet + ", " + numberOfRecords);

            List<User> findUsers = new ArrayList<>();
            while (resultSet.next()){
                User user = buildEntity(resultSet);

                findUsers.add(user);
            }

            resultSet = statement.executeQuery(SQL_SELECT_FOUND_ROWS);
            if (resultSet.next()){
                this.numberOfRecords = resultSet.getInt(FIRST_COLUMN_INDEX);
            }

            return findUsers;
        }catch (SQLException exception) {
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
    public void insert(User entity) throws DAOException {
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

            if (queryResult != 1){
                throw new DAOException("Insert failed.");
            }
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    @Override
    public void update(User entity) throws DAOException {

    }

    /**
     * Factory method for User entity creation.
     *
     * @param resultSet the result set of statement.
     * @return
     * @throws DAOException
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
     * Method gets current table name.
     *
     * @return the table name;
     */
    @Override
    public String getTableName() {
        return TABLE_NAME;
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
