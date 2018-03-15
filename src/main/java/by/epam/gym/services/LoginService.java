package by.epam.gym.services;

import by.epam.gym.dao.UserDAO;
import by.epam.gym.entities.user.User;
import by.epam.gym.pool.ConnectionPool;
import by.epam.gym.utils.PasswordEncoder;

import java.sql.Connection;

/**
 * Service class for user log in.
 *
 * @author Eugene Makarenko
 * @see User
 */
public class LoginService {

    public User getAuthorizedUser(String login, String password){
        ConnectionPool connectionPool =  ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        password = PasswordEncoder.encode(password);

        User user = userDAO.findUserByLoginAndPassword(login,password);
        
        connectionPool.returnConnection(connection);

        return user;
    }

}
