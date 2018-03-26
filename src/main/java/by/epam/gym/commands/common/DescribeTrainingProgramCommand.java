package by.epam.gym.commands.common;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.ExerciseService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import static by.epam.gym.utils.ConfigurationManager.DESCRIBE_TRAINING_PROGRAM_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;

/**
 * Command to describe training program.
 *
 *@author Eugene Makarenko
 */
public class DescribeTrainingProgramCommand implements ActionCommand {

    private static final String TRAINING_PROGRAM_ID_PARAMETER = "programId";

    private static final String DAYS_ATTRIBUTE = "days";

    /**
     * Implementation of command to describe training program.
     *
     * @param request HttpServletRequest object.
     * @return Page object.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl;

        try {
            String trainingProgramIdValue = request.getParameter(TRAINING_PROGRAM_ID_PARAMETER);
            int trainingProgramId = Integer.parseInt(trainingProgramIdValue);

            ExerciseService exerciseService = new ExerciseService();

            Map<Integer, List<Exercise>> exercisesPerDays = exerciseService.showExerciseFromTrainingProgram(trainingProgramId);
            request.setAttribute(DAYS_ATTRIBUTE, exercisesPerDays);

            pageUrl = ConfigurationManager.getProperty(DESCRIBE_TRAINING_PROGRAM_PAGE_PATH);
            page.setRedirect(false);

        } catch (ServiceException exception) {
            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }
        page.setPageUrl(pageUrl);
        return page;
    }
}
