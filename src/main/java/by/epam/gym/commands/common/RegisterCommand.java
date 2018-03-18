package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;

import javax.servlet.http.HttpServletRequest;

/**
 * Command for user registration.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see by.epam.gym.entities.user.User
 */
public class RegisterCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        return null;
    }
}
