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
import static by.epam.gym.utils.MessageManager.EXERCISE_ADDED_SUCCESSFULLY_MESSAGE_PATH;

/**
 * Command to add exercise to training program.
 *
 * @author Eugene Makarenko
 * @see Exercise
 * @see by.epam.gym.entities.TrainingProgram
 * @see ActionCommand
 * @see ExerciseService
 */
public class AddExerciseToTrainingProgramCommand implements ActionCommand {


    private static final String EXERCISE_ID_PARAMETER = "exerciseId";
    private static final String DAY_NUMBER_PARAMETER = "dayNumber";
    private static final String SETS_COUNT_PARAMETER = "setsCount";
    private static final String REPEATS_COUNT_PARAMETER = "repeatsCount";

    private static final String DAYS_ATTRIBUTE = "days";
    private static final String RESULT_ATTRIBUTE = "result";
    private static final String PROGRAM_ID_PARAMETER = "programId";
    /**
     * Implementation of command to add exercise to training program.
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
            Map<Integer, List<Exercise>> daysAndExercises = (Map<Integer, List<Exercise>>) session.getAttribute(DAYS_ATTRIBUTE);

            String dayNumberValue = request.getParameter(DAY_NUMBER_PARAMETER);
            int dayNumber = Integer.parseInt(dayNumberValue);
            List<Exercise> exercises = daysAndExercises.get(dayNumber);

            ExerciseService exerciseService = new ExerciseService();
            String exerciseIdValue = request.getParameter(EXERCISE_ID_PARAMETER);
            int exerciseId = Integer.parseInt(exerciseIdValue);
            Exercise exercise = exerciseService.findExerciseById(exerciseId);

            int numberOfExecution = exercises.size()+1;
            int programId = (int) session.getAttribute(PROGRAM_ID_PARAMETER);

            String setsCountValue = request.getParameter(SETS_COUNT_PARAMETER);
            int setsCount = Integer.parseInt(setsCountValue);

            String repeatsCountValue = request.getParameter(REPEATS_COUNT_PARAMETER);
            int repeatsCount = Integer.parseInt(repeatsCountValue);

            exercise.setSetsCount(setsCount);
            exercise.setRepeatsCount(repeatsCount);
            exercises.add(exercise);

            pageUrl = ConfigurationManager.getProperty(ADD_EXERCISE_PAGE_PATH);
            page.setRedirect(false);

        } catch (ServiceException exception) {
            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }


        page.setPageUrl(pageUrl);
        return page;
    }
}
