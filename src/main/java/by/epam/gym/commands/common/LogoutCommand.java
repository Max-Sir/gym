package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.user.User;
import by.epam.gym.servlet.Page;

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

        return new Page(Page.MAIN_PAGE_PATH, true);
    }
}
