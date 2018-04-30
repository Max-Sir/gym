package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.user.User;
import by.epam.gym.servlet.Page;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Command for user log out.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 */
public class LogoutCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);

    /**
     * Implementation of commands that user use to sign out
     *
     * @param request HttpServletRequest object
     * @return page
     */
    public Page execute(HttpServletRequest request) {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        String login = user.getLogin();

        session.invalidate();

        LOGGER.info(String.format("User: login - %s logged out successful.", login));
        return new Page(Page.MAIN_PAGE_PATH, true);
    }
}
