package by.epam.gym.commands.admin;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.UserService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.FIND_CLIENT_BY_NAME_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.RESULT_ATTRIBUTE;
import static by.epam.gym.utils.MessageManager.USER_DID_NOT_FIND_MESSAGE_PATH;

/**
 * Command to find client by name.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 * @see UserService
 */
public class FindUserByNameCommand implements ActionCommand {

    private static final String FIRST_NAME_PARAMETER = "firstName";
    private static final String LAST_NAME_PARAMETER = "lastName";

    private static final String LIST_ATTRIBUTE = "list";

    /**
     * Implementation of command to find user by name.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        String firstName = request.getParameter(FIRST_NAME_PARAMETER);
        String lastName = request.getParameter(LAST_NAME_PARAMETER);
        Page page = new Page();
        String pageUrl;

        try{
            UserService userService = new UserService();
            List<User> findUsers = userService.findClientByName(firstName,lastName);

            if (findUsers.isEmpty()){
                request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(USER_DID_NOT_FIND_MESSAGE_PATH));
            } else {
                request.setAttribute(LIST_ATTRIBUTE, findUsers);
            }

            pageUrl = ConfigurationManager.getProperty(FIND_CLIENT_BY_NAME_PAGE_PATH);
            page.setRedirect(false);
        } catch (ServiceException exception) {
            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }

        page.setPageUrl(pageUrl);
        return page;
    }
}
