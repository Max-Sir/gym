package by.epam.gym.commands.admin;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.UserService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.FIND_CLIENT_BY_NAME_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.SHOW_ALL_CLIENTS_PAGE_PATH;

/**
 * Command to show all clients.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 * @see UserService
 */
public class ShowAllClientsCommand implements ActionCommand {

    private static final String LIST_ATTRIBUTE = "list";
    private static final String NUMBER_OF_PAGE_ATTRIBUTE = "numberOfPages";
    private static final String CURRENT_PAGE_INDEX_ATTRIBUTE = "pageIndex";

    private static final String PAGE_PARAMETER = "page";

    private static final int MAX_RECORDS_PER_PAGE_COUNT = 10;
    private static final int FIRST_PAGE_INDEX = 1;

    /**
     * Implementation of command to show all clients.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl;

        int pageIndex = FIRST_PAGE_INDEX;

        String pageParameterValue = request.getParameter(PAGE_PARAMETER);

        if (pageParameterValue != null){
            pageIndex = Integer.parseInt(pageParameterValue);
        }

        try {
            int currentOffSet = (pageIndex - 1)*MAX_RECORDS_PER_PAGE_COUNT;

            UserService userService = new UserService();
            Map<List<User>, Integer> clients = userService.findAllClientsByPages(currentOffSet, MAX_RECORDS_PER_PAGE_COUNT);
            Set<Map.Entry<List<User>, Integer>> entries = clients.entrySet();

            List<User> foundClients = null;
            Integer numberOfRecords = null;

            for (Map.Entry<List<User>, Integer> entry : entries) {
                foundClients = entry.getKey();
                numberOfRecords = entry.getValue();
            }

            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / MAX_RECORDS_PER_PAGE_COUNT);

            request.setAttribute(NUMBER_OF_PAGE_ATTRIBUTE,numberOfPages);
            request.setAttribute(CURRENT_PAGE_INDEX_ATTRIBUTE, pageIndex);
            request.setAttribute(LIST_ATTRIBUTE, foundClients);

            pageUrl = ConfigurationManager.getProperty(SHOW_ALL_CLIENTS_PAGE_PATH);
            page.setRedirect(false);
        }  catch (ServiceException e) {
            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }

        page.setPageUrl(pageUrl);
        return page;
    }
}
