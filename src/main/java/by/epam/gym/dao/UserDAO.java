package by.epam.gym.dao;

import by.epam.gym.entities.user.User;
import by.epam.gym.entities.user.UserGender;
import by.epam.gym.entities.user.UserType;
import by.epam.gym.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * The class of DAO level that provide access to the database and deal with User entity.
 *
 * @author Eugene Makarenko
 * @see AbstractDAO
 * @see Connection
 * @see User
 */
public class UserDAO extends AbstractDAO<Integer, User> {

    private static final String SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login=? and password=?";

    /**
     * Instantiates a new UserDAO.
     *
     * @param connection the connection to database.
     */
    public UserDAO(Connection connection) {
        super(connection);
    }

    /**
     * This method finds user by login and password.
     *
     * @param login    the users's login.
     * @param password the user's password.
     * @return User.
     * @throws DAOException object if execution of query is failed.
     */
    public User findUserByLoginAndPassword(String login, String password) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            User user = createEntity(resultSet);

            return user;
        } catch (SQLException exception) {
            throw new DAOException("SQLException detected.", exception);
        }
    }

    /**
     * This method finds all users in the table.
     *
     * @return List of users.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public List<User> findAll() throws DAOException {
        return null;
    }

    /**
     * This method finds user by id.
     *
     * @param id the entity id.
     * @return User.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public User findEntityById(Integer id) throws DAOException {
        return null;
    }

    /**
     * This method deletes chosen user.
     *
     * @param entity the chosen user.
     * @return true if delete will be made successful and false if not.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public boolean delete(User entity) throws DAOException {
        return false;
    }

    /**
     * This method creates user in database.
     *
     * @param entity the chosen user.
     * @return true if creation will be successfull and false if not.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public boolean create(User entity) throws DAOException {
        return false;
    }

    /**
     * This method update chosen user.
     *
     * @param entity the chosen user.
     * @return true if update will be successful and false if not.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public boolean update(User entity) throws DAOException {
        return false;
    }

    /**
     * This method creates User from ResultSet.
     *
     * @param resultSet the result set of statement.
     * @return the user.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public User createEntity(ResultSet resultSet) throws DAOException {
        try {
            User user = null;

            if (resultSet.next()) {
                user = new User();

                int id = resultSet.getInt("id");
                user.setId(id);

                String login = resultSet.getString("login");
                user.setLogin(login);

                String password = resultSet.getString("password");
                user.setPassword(password);

                String userTypeValue = resultSet.getString("type");
                UserType userType = UserType.valueOf(userTypeValue);
                user.setUserType(userType);

                String userGenderValue = resultSet.getString("gender");
                UserGender userGender = UserGender.valueOf(userGenderValue);
                user.setUserGender(userGender);

                String firstName = resultSet.getString("first_name");
                user.setFirstName(firstName);

                String lastName = resultSet.getString("last_name");
                user.setLastName(lastName);

                String email = resultSet.getString("email");
                user.setEmail(email);

                String phoneNumber = resultSet.getString("phone_number");
                user.setPhoneNumber(phoneNumber);

                int personalTrainerId = resultSet.getInt("personal_trainer_id");
                user.setPersonalTrainerId(personalTrainerId);

                Date birthDate = resultSet.getDate("birthdate");
                user.setBirthDate(birthDate);
            }

            return user;
        } catch (SQLException exception) {
            throw new DAOException("SQLException detected.", exception);
        }
    }

}
