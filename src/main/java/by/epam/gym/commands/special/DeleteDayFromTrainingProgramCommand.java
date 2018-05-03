package by.epam.gym.commands.special;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.service.TrainingProgramService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.TrainingProgramDataValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.TreeMap;

import static by.epam.gym.utils.MessageManager.DAY_CAN_NOT_BE_DELETED_MESSAGE_KEY;

/**
 * Command to delete day from training program.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.TrainingProgram
 * @see ActionCommand
 */
public class DeleteDayFromTrainingProgramCommand implements ActionCommand {

    /**
     * Implementation of command to delete day from training program.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        String dayNumberValue = request.getParameter(DAY_NUMBER_PARAMETER);
        HttpSession session = request.getSession();
        TreeMap<Integer, List<Exercise>> daysAndExercises = (TreeMap<Integer, List<Exercise>>) session.getAttribute(DAYS_AND_EXERCISES_ATTRIBUTE);
        TrainingProgramDataValidator trainingProgramDataValidator = new TrainingProgramDataValidator();
        boolean isDataValid = trainingProgramDataValidator.checkDaysCountForDeleteOperation(daysAndExercises);
        if (!isDataValid) {
            return new Page(Page.EDIT_TRAINING_PROGRAM_PAGE_PATH, false, DAY_CAN_NOT_BE_DELETED_MESSAGE_KEY);
        }

        TrainingProgramService trainingProgramService = new TrainingProgramService();
        TreeMap<Integer, List<Exercise>> changedDaysAndExercises = trainingProgramService.deleteDayFromTrainingProgram(dayNumberValue, daysAndExercises);

        session.setAttribute(DAYS_AND_EXERCISES_ATTRIBUTE, changedDaysAndExercises);

        return new Page(Page.EDIT_TRAINING_PROGRAM_PAGE_PATH, false);
    }
}
