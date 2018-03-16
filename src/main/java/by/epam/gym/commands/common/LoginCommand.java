package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.dao.UserDAO;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.DAOException;
import by.epam.gym.pool.ConnectionPool;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;
import by.epam.gym.utils.PasswordEncoder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

import static by.epam.gym.utils.ConfigurationManager.LOGIN_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.MAIN_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.ERROR_MESSAGE_PATH;

/**
 * Command for user log in.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 */
public class LoginCommand implements ActionCommand {

    private final static Logger LOGGER = Logger.getLogger(LoginCommand.class);

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    private static final String USER_SESSION_ATTRIBUTE = "user";
    private static final String ERROR_LOGIN_ATTRIBUTE = "errorLoginPassMessage";

    /**
     * Implementation of command that user use to log in.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     */
    public String execute(HttpServletRequest request) {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        password = PasswordEncoder.encode(password);

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        UserDAO userDAO = new UserDAO(connection);

        try {
            User user = userDAO.findUserByLoginAndPassword(login, password);

            if (user != null) {
                HttpSession currentSession = request.getSession();
                currentSession.setAttribute(USER_SESSION_ATTRIBUTE, user);

                page = ConfigurationManager.getProperty(MAIN_PAGE_PATH);
            } else {
                String errorMessage = MessageManager.getProperty(ERROR_MESSAGE_PATH);
                request.setAttribute(ERROR_LOGIN_ATTRIBUTE, errorMessage);

                page = ConfigurationManager.getProperty(LOGIN_PAGE_PATH);
            }

        } catch (DAOException exception) {
            LOGGER.error("DAO exception detected.", exception);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return page;
    }
}
