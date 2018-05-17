package by.epam.gym.commands.special;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.TrainingProgram;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.ExerciseService;
import by.epam.gym.service.TrainingProgramService;
import by.epam.gym.commands.Page;
import by.epam.gym.utils.TrainingProgramDataValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.TreeMap;

import static by.epam.gym.commands.Page.DESCRIBE_TRAINING_PROGRAM_PAGE_PATH;
import static by.epam.gym.commands.Page.EDIT_TRAINING_PROGRAM_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.*;

/**
 * Command to finish training program edit.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.TrainingProgram
 * @see ActionCommand
 */
public class SaveTrainingProgramCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(SaveTrainingProgramCommand.class);

    /**
     * Implementation of command to finish training program creation.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {

        try {
            HttpSession session = request.getSession();
            TreeMap<Integer, List<Exercise>> daysAndExercises = (TreeMap<Integer, List<Exercise>>) session.getAttribute(DAYS_AND_EXERCISES_ATTRIBUTE);
            TrainingProgramDataValidator trainingProgramDataValidator = new TrainingProgramDataValidator();
            boolean isDaysAndExercisesCountValid = trainingProgramDataValidator.checkDaysAndExercisesCount(daysAndExercises);
            if (!isDaysAndExercisesCountValid) {
                return new Page(EDIT_TRAINING_PROGRAM_PAGE_PATH, false, TRAINING_PROGRAM_DAYS_AND_EXERCISES_NOT_VALID_MESSAGE_KEY);
            }

            TrainingProgram trainingProgram = (TrainingProgram) session.getAttribute(TRAINING_PROGRAM_ATTRIBUTE);
            int trainingProgramId = trainingProgram.getId();
            TrainingProgramService trainingProgramService = new TrainingProgramService();
            boolean isUpdateSuccessful = trainingProgramService.updateTrainingProgram(trainingProgram);

            ExerciseService exerciseService = new ExerciseService();
            boolean isResultSuccessful = exerciseService.addExercisesToTrainingProgram(trainingProgramId, daysAndExercises, true);
            if (!isResultSuccessful || !isUpdateSuccessful) {
                return new Page(EDIT_TRAINING_PROGRAM_PAGE_PATH, false, TRAINING_PROGRAM_NOT_SAVED_MESSAGE_KEY);
            }

            session.removeAttribute(EXERCISES_ATTRIBUTE);
            session.setAttribute(IS_RECORD_INSERTED, true);

            return new Page(DESCRIBE_TRAINING_PROGRAM_PAGE_PATH, false, TRAINING_PROGRAM_SAVED_SUCCESSFUL_MESSAGE_KEY);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }
}
