package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.servlet.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * Empty command is using for stab. Redirect to main page if command wasn't identify.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 */
public class EmptyCommand implements ActionCommand {

    /**
     * Implementation of commands. Redirect to main page.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    public Page execute(HttpServletRequest request) {
        return new Page(Page.MAIN_PAGE_PATH, false);
    }
}
