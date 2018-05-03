package by.epam.gym.commands.trainer;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.ExerciseService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ExerciseDataValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.gym.servlet.Page.CREATE_EXERCISE_PAGE_PATH;
import static by.epam.gym.servlet.Page.SHOW_EXERCISE;
import static by.epam.gym.utils.MessageManager.EXERCISE_CREATION_FAILED_MESSAGE_KEY;
import static by.epam.gym.utils.MessageManager.INVALID_INPUT_DATA_MESSAGE_KEY;

/**
 * Command to create exercise in database.
 *
 * @author Eugene Makarenko
 * @see Exercise
 * @see ActionCommand
 */
public class CreateExerciseCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(CreateExerciseCommand.class);

    /**
     * Implementation of command to create exercise in database.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {

        try {
            String name = request.getParameter(NAME_PARAMETER);
            String levelValue = request.getParameter(LEVEL_PARAMETER);
            String description = request.getParameter(DESCRIPTION_PARAMETER);
            ExerciseDataValidator exerciseDataValidator = new ExerciseDataValidator();
            boolean isDataValid = exerciseDataValidator.checkData(name, levelValue, description);

            if (!isDataValid) {
                return new Page(CREATE_EXERCISE_PAGE_PATH, false, INVALID_INPUT_DATA_MESSAGE_KEY);
            }

            ExerciseService exerciseService = new ExerciseService();
            Exercise exercise = exerciseService.createExercise(name, levelValue, description);
            boolean isOperationSuccessful = exerciseService.saveExercise(exercise);
            if (!isOperationSuccessful) {
                return new Page(CREATE_EXERCISE_PAGE_PATH, false, EXERCISE_CREATION_FAILED_MESSAGE_KEY);
            }

            HttpSession session = request.getSession();
            session.setAttribute(IS_RECORD_INSERTED, true);
            request.setAttribute(EXERCISE_ATTRIBUTE, exercise);

            return new Page(SHOW_EXERCISE, false);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }
}
