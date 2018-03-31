package by.epam.gym.commands.common.training;

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
import static by.epam.gym.utils.ConfigurationManager.EDIT_PROGRAM_PAGE_PATH;

public class EditExerciseCommand implements ActionCommand {

    private static final String EXERCISE_ID_PARAMETER = "exerciseId";
    private static final String DAY_NUMBER_PARAMETER = "dayNumber";
    private static final String SETS_COUNT_ATTRIBUTE = "setsCount";
    private static final String REPEATS_COUNT_ATTRIBUTE = "repeatsCount";
    private static final String DAYS_ATTRIBUTE = "days";

    @Override
    public Page execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String exerciseIdValue = request.getParameter(EXERCISE_ID_PARAMETER);
        int exerciseId = Integer.parseInt(exerciseIdValue);

        String dayNumberValue = request.getParameter(DAY_NUMBER_PARAMETER);
        int dayNumber = Integer.parseInt(dayNumberValue);

        String setsCountValue = request.getParameter(SETS_COUNT_ATTRIBUTE);
        int setsCount = Integer.parseInt(setsCountValue);

        String repeatsCountValue = request.getParameter(REPEATS_COUNT_ATTRIBUTE);
        int repeatsCount = Integer.parseInt(repeatsCountValue);

        Page page = new Page();
        String pageUrl = ConfigurationManager.getProperty(EDIT_PROGRAM_PAGE_PATH);
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
                        exercise.setSetsCount(setsCount);
                        exercise.setRepeatsCount(repeatsCount);
                    }
                }
            }
        }

        return page;
    }
}
