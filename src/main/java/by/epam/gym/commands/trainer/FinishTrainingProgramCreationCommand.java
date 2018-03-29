package by.epam.gym.commands.trainer;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Command to finish training program creation.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.TrainingProgram
 * @see ActionCommand
 */
public class FinishTrainingProgramCreationCommand implements ActionCommand {

    private static final String DAYS_ATTRIBUTE = "days";
    private static final String EXERCISES_ATTRIBUTE = "exercises";
    private static final String PROGRAM_ID_ATTRIBUTE = "programId";

    /**
     * Implementation of command to finish training program creation.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl = ConfigurationManager.getProperty(ConfigurationManager.TRAINER_PAGE_PATH);

        HttpSession session = request.getSession();
        session.removeAttribute(DAYS_ATTRIBUTE);
        session.removeAttribute(EXERCISES_ATTRIBUTE);
        session.removeAttribute(PROGRAM_ID_ATTRIBUTE);

        page.setRedirect(true);
        page.setPageUrl(pageUrl);
        return page;
    }
}
