package by.epam.gym.commands.admin;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.UserService;
import by.epam.gym.servlet.Page;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static by.epam.gym.servlet.Page.SHOW_ALL_CLIENTS_PAGE_PATH;

/**
 * Command to show all clients.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see HttpServletRequest
 * @see UserService
 */
public class ShowAllClientsCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(ShowAllClientsCommand.class);

    private static final int MAX_RECORDS_PER_PAGE_COUNT = 10;
    private static final int FIRST_PAGE_INDEX = 1;

    /**
     * Implementation of command to show all clients.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {

        try {
            int pageIndex = FIRST_PAGE_INDEX;

            String pageParameterValue = request.getParameter(PAGE_PARAMETER);

            if (pageParameterValue != null) {
                pageIndex = Integer.parseInt(pageParameterValue);
            }
            int currentOffSet = (pageIndex - 1) * MAX_RECORDS_PER_PAGE_COUNT;

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

            request.setAttribute(NUMBER_OF_PAGE_ATTRIBUTE, numberOfPages);
            request.setAttribute(CURRENT_PAGE_INDEX_ATTRIBUTE, pageIndex);
            request.setAttribute(LIST_ATTRIBUTE, foundClients);

            return new Page(SHOW_ALL_CLIENTS_PAGE_PATH, false);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }
}
