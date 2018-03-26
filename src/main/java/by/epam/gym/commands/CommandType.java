package by.epam.gym.commands;


import by.epam.gym.commands.admin.FindUserByNameCommand;
import by.epam.gym.commands.admin.ShowAllClientsCommand;
import by.epam.gym.commands.common.LoginCommand;
import by.epam.gym.commands.common.LogoutCommand;
import by.epam.gym.commands.common.RegisterCommand;
import by.epam.gym.commands.trainer.AddExerciseCommand;
import by.epam.gym.commands.trainer.ShowPersonalClientsCommand;

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
    REGISTER {
        {
            command = new RegisterCommand();
        }
    },
    FIND_USER_BY_NAME{
        {
            this.command = new FindUserByNameCommand();
        }
    },
    SHOW_ALL_CLIENTS{
        {
            this.command = new ShowAllClientsCommand();
        }
    },
    ADD_EXERCISE{
        {
            this.command = new AddExerciseCommand();
        }
    },
    SHOW_PERSONAL_CLIENTS{
        {this.command = new ShowPersonalClientsCommand();}
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
