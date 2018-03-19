package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class CheckLoginForUniqueCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        try {
            return String.valueOf(new UserService().checkUserLoginForUnique(request.getParameter("login")));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "false";
    }
}
