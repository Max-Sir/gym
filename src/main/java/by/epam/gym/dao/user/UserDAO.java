package by.epam.gym.dao.user;

import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.DAOException;

/**
 * Interface of guest that describes possible guest's actions.
 *
 * @author Eugene Makarenko
 * @see User
 * @see UserDAOImpl
 */
public interface UserDAO {

    /**
     * SQL query to database for searching user by it's login and password.
     */
    String FIND_BY_LOGIN_AND_PASSWORD_SQL_QUERY = "SELECT * FROM users WHERE login=? AND password=?";

    /**
     * SQL query to database for searching user's login.
     */
    String CHECK_LOGIN_FOR_UNIQUE = "SELECT * FROM users WHERE login=?";

    /**
     * Finds user in database by it's login and password.
     *
     * @param login    the user's login.
     * @param password the user's password.
     * @return the User object.
     * @throws DAOException object if execution of query is failed.
     */
    User findUserByLoginAndPassword(String login, String password) throws DAOException;

    /**
     * Check user's login for unique.
     *
     * @param login the user's login.
     * @return true if login is unique, else returns false.
     * @throws DAOException object if execution of query is failed.
     */
    boolean checkLoginForUnique(String login) throws DAOException;
}
