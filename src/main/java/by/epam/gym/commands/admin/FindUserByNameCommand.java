package by.epam.gym.commands.admin;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.UserService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.DESCRIBE_CLIENT_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.USER_DID_NOT_FIND_MESSAGE;

/**
 * Command to find client by name.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 * @see UserService
 */
public class FindUserByNameCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(FindUserByNameCommand.class);


    /**
     * Implementation of command to find user by name.
     *
     * @param request HttpServletRequest object.
     * @return  page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl;

        try{
            String name = request.getParameter(NAME_PARAMETER);

            UserService userService = new UserService();
            List<User> findUsers = userService.findClientByName(name);

            if (findUsers.isEmpty()){
                String message = MessageManager.getProperty(USER_DID_NOT_FIND_MESSAGE);
                request.setAttribute(RESULT_ATTRIBUTE, message);
                pageUrl = ConfigurationManager.getProperty(DESCRIBE_CLIENT_PAGE_PATH);
            } else {
                request.setAttribute(LIST_ATTRIBUTE, findUsers);
                pageUrl = ConfigurationManager.getProperty(DESCRIBE_CLIENT_PAGE_PATH);

                LOGGER.info(String.format("Clients for name - %s were/was founded successful.",name));
            }

            page.setRedirect(false);
        } catch (ServiceException exception) {
            LOGGER.error("Service exception detected. ", exception);

            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }

        page.setPageUrl(pageUrl);
        return page;
    }
}