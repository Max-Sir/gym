package by.epam.gym.commands;

import by.epam.gym.servlet.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface Action command. Realization of pattern - Command.
 *
 * @author Eugene Makarenko
 */
public interface ActionCommand {

    /**
     * Need to be implemented by command classes.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     * @see HttpServletRequest
     */
    Page execute(HttpServletRequest request);
}
