package by.epam.gym.commands;

import by.epam.gym.commands.common.EmptyCommand;
import by.epam.gym.utils.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {

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
                    + MessageManager.getProperty("message.wrongaction"));
        }
        return currentCommand;
    }

}
