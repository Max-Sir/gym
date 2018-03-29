package by.epam.gym.commands.trainer;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.ExerciseService;
import by.epam.gym.service.TrainingProgramService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

import static by.epam.gym.utils.ConfigurationManager.ADD_EXERCISE_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.EXERCISE_DELETE_FAILED_MESSAGE_PATH;
import static by.epam.gym.utils.MessageManager.RESULT_ATTRIBUTE;

/**
 * Command to delete exercise from training program.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.TrainingProgram
 * @see by.epam.gym.entities.exercise.Exercise
 * @see by.epam.gym.service.ExerciseService
 * @see ActionCommand
 */
public class DeleteExerciseFromTrainingProgramCommand implements ActionCommand {

    private static final String TRAINING_PROGRAM_ID_PARAMETER = "programId";
    private static final String EXERCISE_ID_PARAMETER = "exerciseId";
    private static final String DAY_NUMBER_PARAMETER = "dayNumber";

    private static final String DAYS_ATTRIBUTE = "days";

    /**
     * Implementation of command to delete exercise from training program.
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

            int trainingProgramId = (int) session.getAttribute(TRAINING_PROGRAM_ID_PARAMETER);

            String exerciseIdValue = request.getParameter(EXERCISE_ID_PARAMETER);
            int exerciseId = Integer.parseInt(exerciseIdValue);

            String dayNumberValue = request.getParameter(DAY_NUMBER_PARAMETER);
            int dayNumber = Integer.parseInt(dayNumberValue);

            ExerciseService exerciseService = new ExerciseService();
            boolean isOperationSuccessful = exerciseService.deleteExerciseFromTrainingProgram(trainingProgramId,exerciseId,dayNumber);

            if (isOperationSuccessful){
                pageUrl = ConfigurationManager.getProperty(ADD_EXERCISE_PAGE_PATH);
                Map<Integer, String> exercisesIdAndName = exerciseService.findAllExercisesIdAndName();
                session.setAttribute(DAYS_ATTRIBUTE,exercisesIdAndName);
            } else {
                request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(EXERCISE_DELETE_FAILED_MESSAGE_PATH));
                pageUrl = ConfigurationManager.getProperty(ADD_EXERCISE_PAGE_PATH);
            }

            page.setRedirect(false);
        }catch (ServiceException exception) {
            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }

        page.setPageUrl(pageUrl);
        return page;
    }
}
