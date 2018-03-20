package by.epam.gym.commands;

import by.epam.gym.commands.common.EmptyCommand;
import by.epam.gym.utils.MessageManager;

import javax.servlet.http.HttpServletRequest;

import static by.epam.gym.utils.MessageManager.WRONG_ACTION_MESSAGE_PATH;

/**
 * Factory class for creation commands.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 */
public class CommandFactory {

    private static final String COMMAND_PARAMETER = "command";

    private static final String WRONG_ACTION_ATTRIBUTE = "wrongAction";

    /**
     * This method define command and return it's instance.
     *
     * @param request the HttpServletRequest request.
     * @return the defined command.
     */
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand currentCommand = new EmptyCommand();

        String action = request.getParameter(COMMAND_PARAMETER);
        if (action == null || action.isEmpty()) {
            return currentCommand;
        }
        try {
            String commandTypeValue = action.toUpperCase();
            CommandType currentType = CommandType.valueOf(commandTypeValue);
            currentCommand = currentType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute(WRONG_ACTION_ATTRIBUTE, action
                    + MessageManager.getProperty(WRONG_ACTION_MESSAGE_PATH));
        }
        return currentCommand;
    }

}
