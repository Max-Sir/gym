package by.epam.gym.commands.special;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.ExerciseService;
import by.epam.gym.servlet.Page;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.TreeMap;

import static by.epam.gym.utils.MessageManager.INVALID_INPUT_DATA_MESSAGE_KEY;

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

    private static final Logger LOGGER = Logger.getLogger(AddExerciseToTrainingProgramCommand.class);

    /**
     * Implementation of command to add exercise to training program.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        try {
            String exerciseIdValue = request.getParameter(EXERCISE_ID_PARAMETER);
            String dayNumberValue = request.getParameter(DAY_NUMBER_PARAMETER);
            String setsCountValue = request.getParameter(SETS_COUNT_PARAMETER);
            String repeatsCountValue = request.getParameter(REPEATS_COUNT_PARAMETER);
            HttpSession session = request.getSession();
            TreeMap<Integer, List<Exercise>> daysAndExercises = (TreeMap<Integer, List<Exercise>>) session.getAttribute(DAYS_AND_EXERCISES_ATTRIBUTE);
            ExerciseService exerciseService = new ExerciseService();
            boolean isOperationSuccessful = exerciseService.addExerciseInTrainingProgram(exerciseIdValue, dayNumberValue, setsCountValue, repeatsCountValue, daysAndExercises);
            if (!isOperationSuccessful) {
                return new Page(Page.EDIT_TRAINING_PROGRAM_PAGE_PATH, false, INVALID_INPUT_DATA_MESSAGE_KEY);
            }

            LOGGER.info("Exercise was added to training program.");
            return new Page(Page.EDIT_TRAINING_PROGRAM_PAGE_PATH, false);
        } catch (ServiceException exception) {
            LOGGER.error(String.format("Service exception detected in command - %s. ", getClass().getSimpleName()), exception);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }
}
