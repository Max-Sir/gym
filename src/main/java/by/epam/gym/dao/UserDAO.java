package by.epam.gym.dao;

import by.epam.gym.entities.user.User;

import java.sql.Connection;
import java.util.List;

/**
 * The class of DAO level that provide access to the database and deal with User entity.
 *
 *@author Eugene Makarenko
 * @see AbstractDAO
 * @see Connection
 * @see User
 */
public class UserDAO extends AbstractDAO<Integer, User> {

    private static final String SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login=? and password=?";

    public UserDAO(Connection connection) {
        super(connection);
    }

    public User findUserByLoginAndPassword(String login, String password){
        User user = null;

        return user;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }

    @Override
    public boolean create(User entity) {
        return false;
    }

    @Override
    public boolean update(User entity) {
        return false;
    }
}
