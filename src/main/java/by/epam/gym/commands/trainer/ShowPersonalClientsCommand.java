package by.epam.gym.commands.trainer;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.UserService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Map;

import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.PERSONAL_CLIENTS_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.NO_PERSONAL_CLIENTS_MESSAGE_PATH;
import static by.epam.gym.utils.MessageManager.RESULT_ATTRIBUTE;

/**
 * Command to show all personal clients.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.user.User
 * @see ActionCommand
 */
public class ShowPersonalClientsCommand implements ActionCommand {

    private static final String USER_SESSION_ATTRIBUTE = "user";
    private static final String LIST_ATTRIBUTE = "list";
    /**
     * Implementation of command to show all personal clients.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl;

        try{
            HttpSession session = request.getSession();
            User trainer = (User) session.getAttribute(USER_SESSION_ATTRIBUTE);
            int trainerId = trainer.getId();

            UserService userService = new UserService();
            Map<User, Integer> clientsAndTrainingProgramsId = userService.findPersonalClients(trainerId);
            if (clientsAndTrainingProgramsId.isEmpty()){
                request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(NO_PERSONAL_CLIENTS_MESSAGE_PATH));
            } else {
                request.setAttribute(LIST_ATTRIBUTE, clientsAndTrainingProgramsId);
            }
            pageUrl = ConfigurationManager.getProperty(PERSONAL_CLIENTS_PAGE_PATH);
            page.setPageUrl(pageUrl);
            page.setRedirect(false);

        }catch (ServiceException exception) {
            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }


        page.setPageUrl(pageUrl);
        return page;
    }
}
