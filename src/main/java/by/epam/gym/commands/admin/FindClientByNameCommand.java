package by.epam.gym.commands.admin;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.UserService;
import by.epam.gym.commands.Page;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.epam.gym.commands.Page.DESCRIBE_CLIENT_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.INFORMATION_NOT_FOUND_MESSAGE_KEY;

/**
 * Command to find client by name.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 * @see UserService
 */
public class FindClientByNameCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(FindClientByNameCommand.class);


    /**
     * Implementation of command to find user by name.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {

        try {
            String name = request.getParameter(NAME_PARAMETER);

            UserService userService = new UserService();
            List<User> findUsers = userService.findClientByName(name);

            if (findUsers.isEmpty()) {
                return new Page(Page.MAIN_PAGE_PATH, false, INFORMATION_NOT_FOUND_MESSAGE_KEY);
            }

            request.setAttribute(LIST_ATTRIBUTE, findUsers);

            return new Page(DESCRIBE_CLIENT_PAGE_PATH, false);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }
}