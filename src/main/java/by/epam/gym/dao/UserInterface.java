package by.epam.gym.dao;

import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.DAOException;

/**
 * Interface of user that describes possible users's actions.
 *
 * @author Eugene Makarenko
 * @see User
 */
public interface UserInterface {

    /**
     * SQL query to database for searching user by it's login and password.
     */
    String FIND_BY_LOGIN_AND_PASSWORD_SQL_QUERY = "SELECT * FROM ? WHERE login=? and password=?";

    /**
     * Finds user in database by it's login and password.
     *
     * @param login the user's login.
     * @param password the user's password.
     * @return the User object.
     * @throws DAOException object if execution of query is failed.
     */
    User findUserByLoginAndPassword(String login, String password) throws DAOException;

}
