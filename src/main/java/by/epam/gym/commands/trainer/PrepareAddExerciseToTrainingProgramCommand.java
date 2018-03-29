package by.epam.gym.commands.trainer;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.ExerciseService;
import by.epam.gym.service.TrainingProgramService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;

/**
 * Command to prepare page for adding exercise into training program.
 *
 * @author Eugene Makarenko
 * @see Exercise
 * @see by.epam.gym.entities.TrainingProgram
 * @see TrainingProgramService
 * @see ActionCommand
 */
public class PrepareAddExerciseToTrainingProgramCommand implements ActionCommand {

    private static final String DAYS_ATTRIBUTE = "days";
    private static final String EXERCISES_ATTRIBUTE = "exercises";
    private static final String PROGRAM_ID_ATTRIBUTE = "programId";
    private static final String USER_ATTRIBUTE = "user";

    /**
     * Implementation of command to prepare page for adding exercise into training program.
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
            Map<Integer, List<Exercise>> daysAndExercises = (Map<Integer, List<Exercise>>) session.getAttribute(DAYS_ATTRIBUTE);
            request.setAttribute(DAYS_ATTRIBUTE, daysAndExercises);

            ExerciseService exerciseService = new ExerciseService();
            Map<Integer, String> exercisesIdAndName = exerciseService.findAllExercisesIdAndName();
            request.setAttribute(EXERCISES_ATTRIBUTE, exercisesIdAndName);
            session.setAttribute(EXERCISES_ATTRIBUTE,exercisesIdAndName);

            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            int authorId = user.getId();

            TrainingProgramService trainingProgramService = new TrainingProgramService();
            int programId = trainingProgramService.lastTrainingProgramId(authorId);

            request.setAttribute(PROGRAM_ID_ATTRIBUTE,programId);

            pageUrl = ConfigurationManager.getProperty(ConfigurationManager.ADD_EXERCISE_PAGE_PATH);
            page.setRedirect(false);

        }catch (ServiceException exception) {
            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }

        page.setPageUrl(pageUrl);
        return page;
    }
}
