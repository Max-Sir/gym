package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.gym.utils.ConfigurationManager.INDEX_PAGE_PATH;

/**
 * Command for user log out.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 */
public class LogoutCommand implements ActionCommand {

    /**
     * Implementation of command that user use to sign out
     *
     * @param request HttpServletRequest object
     * @return redirect page
     */
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(INDEX_PAGE_PATH);
        HttpSession session = request.getSession();
        session.invalidate();

        return page;
    }
}
