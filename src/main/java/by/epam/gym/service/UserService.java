package by.epam.gym.service;

import by.epam.gym.dao.user.UserDAO;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.DAOException;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.pool.ConnectionPool;
import by.epam.gym.utils.PasswordEncoder;

import java.sql.Connection;

public class UserService {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    public User login(String login, String password) throws ServiceException {
        password = PasswordEncoder.encode(password);

        Connection connection = CONNECTION_POOL.getConnection();
        UserDAO userDAO = new UserDAO(connection);

        try {
            User user = userDAO.findUserByLoginAndPassword(login, password);

            return user;
        } catch (DAOException exception) {
            throw new ServiceException("DAO exception detected. " + exception);
        } finally {
            CONNECTION_POOL.returnConnection(connection);
        }
    }

}
