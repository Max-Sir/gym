package by.epam.gym.commands;

import by.epam.gym.commands.common.EmptyCommand;
import by.epam.gym.utils.MessageManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Factory class for creation commands.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 */
public class CommandFactory {

    /**
     * This method define command and return it's instance.
     *
     * @param request the HttpServletRequest request.
     * @return the defined command.
     */
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand currentCommand = new EmptyCommand();

        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return currentCommand;
        }
        try {
            CommandType currentType = CommandType.valueOf(action.toUpperCase());
            currentCommand = currentType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action
                    + MessageManager.getProperty("message.wrongAction"));
        }
        return currentCommand;
    }

}
