package by.epam.gym.commands.trainer;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.ExerciseService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static by.epam.gym.utils.ConfigurationManager.ADD_EXERCISE_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.ADD_EXERCISE_FAILED_MESSAGE_PATH;
import static by.epam.gym.utils.MessageManager.RESULT_ATTRIBUTE;

/**
 * Command to finish training program creation.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.TrainingProgram
 * @see ActionCommand
 */
public class FinishTrainingProgramCreationCommand implements ActionCommand {

    private static final String DAYS_ATTRIBUTE = "days";
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
        String pageUrl;

        try {
            HttpSession session = request.getSession();
            Map<Integer, List<Exercise>> exercisesIdAndName = (Map<Integer, List<Exercise>>) session.getAttribute(DAYS_ATTRIBUTE);
            int programId = (int) session.getAttribute(PROGRAM_ID_ATTRIBUTE);

            ExerciseService exerciseService = new ExerciseService();
            boolean isResultSuccessful = exerciseService.addExercisesToTrainingProgram(programId, exercisesIdAndName);

            if (isResultSuccessful) {
                pageUrl = ConfigurationManager.getProperty(ConfigurationManager.TRAINER_PAGE_PATH);
                page.setRedirect(false);
            } else {
                pageUrl = ConfigurationManager.getProperty(ADD_EXERCISE_PAGE_PATH);
                page.setRedirect(false);
                request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(ADD_EXERCISE_FAILED_MESSAGE_PATH));
            }
        } catch (ServiceException exception) {
            pageUrl = ConfigurationManager.getProperty(ADD_EXERCISE_PAGE_PATH);
            page.setRedirect(false);
            request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(ADD_EXERCISE_FAILED_MESSAGE_PATH));
        }

        page.setPageUrl(pageUrl);
        return page;
    }
}
