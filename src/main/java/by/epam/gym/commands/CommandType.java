package by.epam.gym.commands;


import by.epam.gym.commands.common.LoginCommand;
import by.epam.gym.commands.common.LogoutCommand;
import by.epam.gym.commands.common.RegisterCommand;

/**
 * Types of commands.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 */
public enum CommandType {

    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    REGISTER{
        {
            command = new RegisterCommand();
        }
    };
    /**
     * Current command.
     */
    ActionCommand command;

    /**
     * Gets current command.
     *
     * @return the current command.
     */
    public ActionCommand getCurrentCommand() {
        return command;
    }

}
