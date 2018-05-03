package by.epam.gym.commands.special;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.service.ExerciseService;
import by.epam.gym.servlet.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static by.epam.gym.servlet.Page.EDIT_TRAINING_PROGRAM_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.INVALID_INPUT_DATA_MESSAGE_KEY;


/**
 * Command to edit exercise in training program.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.exercise.Exercise
 * @see by.epam.gym.entities.TrainingProgram
 * @see by.epam.gym.commands.ActionCommand
 */
public class EditExerciseInTrainingProgramCommand implements ActionCommand {

    /**
     * Implementation of command to edit exercise in training program.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        String exerciseIdValue = request.getParameter(EXERCISE_ID_PARAMETER);
        String dayNumberValue = request.getParameter(DAY_NUMBER_PARAMETER);
        String setsCountValue = request.getParameter(SETS_COUNT_PARAMETER);
        String repeatsCountValue = request.getParameter(REPEATS_COUNT_PARAMETER);

        HttpSession session = request.getSession();
        Map<Integer, List<Exercise>> daysAndExercises = (Map<Integer, List<Exercise>>) session.getAttribute(DAYS_AND_EXERCISES_ATTRIBUTE);

        ExerciseService exerciseService = new ExerciseService();
        boolean isOperationSuccessful = exerciseService.editExercise(exerciseIdValue, dayNumberValue, setsCountValue, repeatsCountValue, daysAndExercises);
        if (!isOperationSuccessful) {
            return new Page(EDIT_TRAINING_PROGRAM_PAGE_PATH, false, INVALID_INPUT_DATA_MESSAGE_KEY);
        }

        return new Page(EDIT_TRAINING_PROGRAM_PAGE_PATH, false);
    }
}

