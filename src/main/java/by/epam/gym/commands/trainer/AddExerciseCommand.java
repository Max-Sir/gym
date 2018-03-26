package by.epam.gym.commands.trainer;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.entities.exercise.ExerciseDifficultyLevel;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.ExerciseService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;

import javax.servlet.http.HttpServletRequest;

import static by.epam.gym.utils.ConfigurationManager.ADD_EXERCISE_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.EXERCISE_ADDED_SUCCESSFULLY_MESSAGE_PATH;
import static by.epam.gym.utils.MessageManager.RESULT_ATTRIBUTE;

/**
 * Command to add exercise in database.
 *
 * @author Eugene Makarenko
 * @see Exercise
 * @see ActionCommand
 */
public class AddExerciseCommand implements ActionCommand {

    private static final String NAME_PARAMETER = "name";
    private static final String LEVEL_PARAMETER = "level";
    private static final String DESCRIPTION_PARAMETER = "description";

    /**
     * Implementation of command to add exercise in database.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl;

        String name = request.getParameter(NAME_PARAMETER);
        String levelValue = request.getParameter(LEVEL_PARAMETER);
        String description = request.getParameter(DESCRIPTION_PARAMETER);

        try {
            ExerciseService exerciseService = new ExerciseService();

            ExerciseDifficultyLevel level = ExerciseDifficultyLevel.valueOf(levelValue);
            Exercise exercise = new Exercise();

            exercise.setName(name);
            exercise.setLevel(level);
            exercise.setDescription(description);

            exerciseService.addExerciseToDatabase(exercise);

            pageUrl = ConfigurationManager.getProperty(ADD_EXERCISE_PAGE_PATH);
            page.setRedirect(false);

            String message = MessageManager.getProperty(EXERCISE_ADDED_SUCCESSFULLY_MESSAGE_PATH);

            request.setAttribute(RESULT_ATTRIBUTE, message);
        } catch (ServiceException exception) {
            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }

        page.setPageUrl(pageUrl);
        return page;
    }
}
