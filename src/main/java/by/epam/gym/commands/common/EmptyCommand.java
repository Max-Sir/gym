package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.servlet.Page;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Empty command is using for stab. Redirect to main page if command wasn't identify.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 */
public class EmptyCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(EmptyCommand.class);

    /**
     * Implementation of commands. Redirect to main page.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    public Page execute(HttpServletRequest request) {

        LOGGER.info("Empty command was used. Check log for errors.");
        return new Page(Page.MAIN_PAGE_PATH, false);
    }
}
