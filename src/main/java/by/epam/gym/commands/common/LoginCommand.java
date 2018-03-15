package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command for user log in.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 */
public class LoginCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";


    public String execute(HttpServletRequest request) {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);



        return page;
    }
}
