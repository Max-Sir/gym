package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Empty command. Redirect to index page.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 * @see ConfigurationManager
 */
public class EmptyCommand implements ActionCommand {

    /**
     * Implementation of command. Redirect to index page.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     */
    public Page execute(HttpServletRequest request) {
        String pageUrl = ConfigurationManager.getProperty("path.page.index");

        Page page = new Page(pageUrl, false);

        return page;
    }
}
