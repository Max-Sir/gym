package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;

import javax.servlet.http.HttpServletRequest;

/**
 * Command for user log out.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 */
public class LogoutCommand implements ActionCommand{
    public String execute(HttpServletRequest request) {
        return null;
    }
}
