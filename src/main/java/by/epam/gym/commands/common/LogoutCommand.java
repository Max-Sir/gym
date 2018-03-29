package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.gym.utils.ConfigurationManager.INDEX_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.MAIN_PAGE_PATH;

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
    public Page execute(HttpServletRequest request) {
        String pageUrl = ConfigurationManager.getProperty(MAIN_PAGE_PATH);
        HttpSession session = request.getSession();
        session.invalidate();

        Page page = new Page(pageUrl,false);

        return page;
    }
}
