package by.epam.gym.commands.trainer;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.UserService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static by.epam.gym.utils.ConfigurationManager.CREATE_TRAINING_PROGRAM_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;

/**
 * Command to prepare page for creation training program.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.TrainingProgram
 * @see ActionCommand
 * @see by.epam.gym.service.UserService
 */
public class PrepareTrainingProgramCreationCommand implements ActionCommand {

    private static final String LIST_ATTRIBUTE = "list";

    /**
     * Implementation of command to prepare page for creation training program.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl;

        try{
            UserService userService = new UserService();
            Map<Integer,String> clientsIdAndName = userService.findClientsIdAndName();

            request.setAttribute(LIST_ATTRIBUTE, clientsIdAndName);
            pageUrl = ConfigurationManager.getProperty(CREATE_TRAINING_PROGRAM_PAGE_PATH);
            page.setRedirect(false);

        }catch (ServiceException exception) {
            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }

        page.setPageUrl(pageUrl);
        return page;
    }
}
