package by.epam.gym.commands.trainer;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static by.epam.gym.utils.ConfigurationManager.ADD_EXERCISE_PAGE_PATH;

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

        HttpSession session = request.getSession();

        String exerciseIdValue = request.getParameter(EXERCISE_ID_PARAMETER);
        int exerciseId = Integer.parseInt(exerciseIdValue);

        String dayNumberValue = request.getParameter(DAY_NUMBER_PARAMETER);
        int dayNumber = Integer.parseInt(dayNumberValue);

        String pageUrl = ConfigurationManager.getProperty(ADD_EXERCISE_PAGE_PATH);
        page.setPageUrl(pageUrl);

        Map<Integer, List<Exercise>> exercisesIdAndName = (Map<Integer, List<Exercise>>) session.getAttribute(DAYS_ATTRIBUTE);

        Set<Map.Entry<Integer, List<Exercise>>> entrySet = exercisesIdAndName.entrySet();
        for (Map.Entry<Integer, List<Exercise>> entry : entrySet) {
            int day = entry.getKey();
            if (day == dayNumber) {
                List<Exercise> exercises = entry.getValue();
                Iterator<Exercise> iterator = exercises.iterator();
                while (iterator.hasNext()) {
                    Exercise exercise = iterator.next();
                    int currentExerciseId = exercise.getId();

                    if (exerciseId == currentExerciseId) {
                        iterator.remove();
                    }
                }
            }
        }

        return page;
    }
}
