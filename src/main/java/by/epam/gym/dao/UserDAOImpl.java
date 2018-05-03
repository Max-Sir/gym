package by.epam.gym.dao;

import by.epam.gym.entities.user.User;
import by.epam.gym.entities.user.UserRole;
import by.epam.gym.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that provide access to the database and deal with User entity.
 *
 * @author Eugene Makarenko
 * @see AbstractDAOImpl
 * @see User
 */
public class UserDAOImpl extends AbstractDAOImpl<User> {

    /**
     * Common queries.
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM users WHERE id=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO users (login, password, role, first_name, last_name) VALUES(?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE users SET login=?, password=?, role=?, first_name=?, last_name=? WHERE id=?";

    private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD_QUERY = "SELECT * FROM users WHERE login=? AND password=?";
    private static final String SELECT_USERS_BY_LOGIN_QUERY = "SELECT * FROM users WHERE login=?";
    private static final String SELECT_CLIENTS_BY_FULL_NAME_QUERY = "SELECT * FROM users WHERE first_name=? AND last_name=? AND role='CLIENT'";
    private static final String SELECT_CLIENTS_BY_NAME_PART_QUERY = "SELECT * FROM users WHERE role='CLIENT' AND first_name LIKE ? OR last_name LIKE ?";
    private static final String SELECT_USERS_BY_FOUND_ROWS_QUERY = "SELECT SQL_CALC_FOUND_ROWS * FROM users WHERE role='CLIENT' LIMIT %d, %d";
    private static final String SELECT_FOUND_ROWS_QUERY = "SELECT FOUND_ROWS()";
    private static final String SELECT_PERSONAL_CLIENTS = "SELECT * FROM users WHERE id IN " +
            "(SELECT client_id FROM training_programs WHERE personal_trainer_id=? AND end_date > CURDATE())";

    private static final String SELECT_TRAINING_PROGRAM_AUTHOR_NAME_QUERY = "SELECT first_name, last_name FROM users WHERE id IN" +
            " (SELECT author_id FROM training_programs WHERE id=?)";

    private static final String SELECT_CLIENT_ID_AND_NAME_FOR_TRAINING_PROGRAM_CREATION_QUERY = "call SELECT_ACTUAL_CLIENT()";

    private static final String LOGIN_COLUMN_LABEL = "login";
    private static final String PASSWORD_COLUMN_LABEL = "password";
    private static final String ROLE_COLUMN_LABEL = "role";
    private static final String FIRST_NAME_COLUMN_LABEL = "first_name";
    private static final String LAST_NAME_COLUMN_LABEL = "last_name";

    private static final int FIRST_COLUMN_INDEX = 1;

    private static final String EMPTY_NAME = "";

    private int numberOfRecords;

    /**
     * Instantiates a new UserDAOImpl.
     *
     * @param connection the connection to database.
     */
    public UserDAOImpl(Connection connection) {
        super(connection);
    }

    /**
     * Gets number of records.
     *
     * @return the number of records.
     */
    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    /**
     * This method selects user in database by it's login and password.
     *
     * @param login    the user's login.
     * @param password the user's password.
     * @return the User object.
     * @throws DAOException object if execution of query is failed.
     */
    public User selectUserByLoginAndPassword(String login, String password) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_USER_BY_LOGIN_AND_PASSWORD_QUERY, login, password)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = buildEntity(resultSet);
            }

            return user;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
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
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_USERS_BY_LOGIN_QUERY, login)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method selects users by full name.
     *
     * @param firstName the user's name.
     * @param lastName  the user's name.
     * @return List of found users.
     * @throws DAOException object if execution of query is failed.
     */
    public List<User> selectClientByFullName(String firstName, String lastName) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_CLIENTS_BY_FULL_NAME_QUERY, firstName, lastName)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> findUsers = new ArrayList<>();
            while (resultSet.next()) {
                User user = buildEntity(resultSet);

                findUsers.add(user);
            }

            return findUsers;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method selects client by name part.
     *
     * @param name the user's name.
     * @return List of found users.
     * @throws DAOException object if execution of query is failed.
     */
    public List<User> selectClientByNamePart(String name) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENTS_BY_NAME_PART_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> findUsers = new ArrayList<>();
            while (resultSet.next()) {
                User user = buildEntity(resultSet);

                findUsers.add(user);
            }

            return findUsers;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method select all clients in database.
     *
     * @return List of clients.
     * @throws DAOException object if execution of query is failed.
     */
    public List<User> selectAllClientsByFoundRows(int offSet, int numberOfRecords) throws DAOException {
        try (Statement statement = connection.createStatement()) {
            String sqlQuery = String.format(SELECT_USERS_BY_FOUND_ROWS_QUERY, offSet, numberOfRecords);
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            List<User> findUsers = new ArrayList<>();
            while (resultSet.next()) {
                User user = buildEntity(resultSet);

                findUsers.add(user);
            }

            resultSet = statement.executeQuery(SELECT_FOUND_ROWS_QUERY);
            if (resultSet.next()) {
                this.numberOfRecords = resultSet.getInt(FIRST_COLUMN_INDEX);
            }

            return findUsers;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method selects all clients of current trainer and their training programs id .
     *
     * @param trainerId the trainer id.
     * @return List with clients.
     * @throws DAOException object if execution of query is failed.
     */
    public List<User> selectPersonalClients(int trainerId) throws DAOException {
        try (PreparedStatement findClientsStatement = prepareStatementForQuery(SELECT_PERSONAL_CLIENTS, trainerId)) {
            List<User> clients = new ArrayList<>();
            ResultSet resultSet = findClientsStatement.executeQuery();

            while (resultSet.next()) {
                User user = buildEntity(resultSet);
                clients.add(user);
            }

            return clients;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method selects training program author name.
     *
     * @param trainingProgramId the training program id.
     * @return the name of author.
     * @throws DAOException object if execution of query is failed.
     */
    public String selectTrainingProgramAuthorName(int trainingProgramId) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_TRAINING_PROGRAM_AUTHOR_NAME_QUERY, trainingProgramId)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString(FIRST_NAME_COLUMN_LABEL);
                String lastName = resultSet.getString(LAST_NAME_COLUMN_LABEL);

                return String.format("%s %s", firstName, lastName);
            } else {
                return EMPTY_NAME;
            }
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method selects client's id and name for training program creation operation.
     *
     * @return Map with id and name.
     * @throws DAOException object if execution of query is failed.
     */
    public Map<Integer, String> selectClientIdAndNameForTrainingProgramCreation() throws DAOException {
        try (CallableStatement statement = connection.prepareCall(SELECT_CLIENT_ID_AND_NAME_FOR_TRAINING_PROGRAM_CREATION_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            Map<Integer, String> clientsIdAndName = new HashMap<>();

            while (resultSet.next()) {

                int id = resultSet.getInt(ID_COLUMN_LABEL);
                String firstName = resultSet.getString(FIRST_NAME_COLUMN_LABEL);
                String lastName = resultSet.getString(LAST_NAME_COLUMN_LABEL);
                String fullName = String.format("%s %s", firstName, lastName);

                clientsIdAndName.put(id, fullName);
            }

            return clientsIdAndName;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method gets entity's parameters.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    @Override
    protected List<String> getEntityParameters(User entity) {
        List<String> parameters = new ArrayList<>();

        String login = entity.getLogin();
        parameters.add(login);

        String password = entity.getPassword();
        parameters.add(password);

        UserRole role = entity.getUserRole();
        String roleValue = String.valueOf(role);
        parameters.add(roleValue);

        String firstName = entity.getFirstName();
        parameters.add(firstName);

        String lastName = entity.getLastName();
        parameters.add(lastName);

        return parameters;
    }

    /**
     * This method builds User object from ResultSet object.
     *
     * @param resultSet the result set of statement.
     * @return The User object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    protected User buildEntity(ResultSet resultSet) throws DAOException {
        try {
            User user = new User();

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            user.setId(id);

            String login = resultSet.getString(LOGIN_COLUMN_LABEL);
            user.setLogin(login);

            String password = resultSet.getString(PASSWORD_COLUMN_LABEL);
            user.setPassword(password);

            String userRoleValue = resultSet.getString(ROLE_COLUMN_LABEL);
            UserRole userRole = UserRole.valueOf(userRoleValue);
            user.setUserRole(userRole);

            String firstName = resultSet.getString(FIRST_NAME_COLUMN_LABEL);
            user.setFirstName(firstName);

            String lastName = resultSet.getString(LAST_NAME_COLUMN_LABEL);
            user.setLastName(lastName);

            return user;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method initialize queries for common operations.
     *
     * @return Map object with queries.
     */
    @Override
    protected Map<String, String> initializeCommonQueries() {
        Map<String, String> commonQueries = new HashMap<>();

        commonQueries.put(SELECT_ALL_QUERY_KEY, SELECT_ALL_QUERY);
        commonQueries.put(SELECT_BY_ID_QUERY_KEY, SELECT_BY_ID_QUERY);
        commonQueries.put(DELETE_BY_ID_QUERY_KEY, DELETE_BY_ID_QUERY);
        commonQueries.put(INSERT_ENTITY_QUERY_KEY, INSERT_ENTITY_QUERY);
        commonQueries.put(UPDATE_ENTITY_QUERY_KEY, UPDATE_ENTITY_QUERY);

        return commonQueries;
    }
}
