package by.epam.gym.commands.special;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.service.TrainingProgramService;
import by.epam.gym.servlet.Page;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.TreeMap;

import static by.epam.gym.utils.MessageManager.DAY_ADD_FAILED_MESSAGE_KEY;

/**
 * Command to add day into training program.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.TrainingProgram
 * @see ActionCommand
 */
public class AddDayToTrainingProgramCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(AddDayToTrainingProgramCommand.class);

    /**
     * Implementation of command to add day into training program.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        TreeMap<Integer, List<Exercise>> daysAndExercises = (TreeMap<Integer, List<Exercise>>) session.getAttribute(DAYS_AND_EXERCISES_ATTRIBUTE);
        TrainingProgramService trainingProgramService = new TrainingProgramService();
        boolean isOperationSuccessful = trainingProgramService.addDayInTrainingProgram(daysAndExercises);
        if (!isOperationSuccessful) {
            LOGGER.info("Day wasn't added to training program.");
            return new Page(Page.EDIT_TRAINING_PROGRAM_PAGE_PATH, false, DAY_ADD_FAILED_MESSAGE_KEY);
        }

        LOGGER.info("Day was added to training program.");
        return new Page(Page.EDIT_TRAINING_PROGRAM_PAGE_PATH, false);
    }
}
