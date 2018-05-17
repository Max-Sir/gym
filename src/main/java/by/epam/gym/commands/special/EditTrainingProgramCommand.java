package by.epam.gym.commands.special;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.ExerciseService;
import by.epam.gym.commands.Page;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command to edit training program.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.TrainingProgram
 * @see by.epam.gym.entities.exercise.Exercise
 * @see ActionCommand
 */
public class EditTrainingProgramCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(EditTrainingProgramCommand.class);

    /**
     * Implementation of commands to edit training program.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {

        try {
            ExerciseService exerciseService = new ExerciseService();
            List<Exercise> exercises = exerciseService.findAllExercisesIdAndName();

            HttpSession session = request.getSession();
            session.setAttribute(EXERCISES_ATTRIBUTE, exercises);

            return new Page(Page.EDIT_TRAINING_PROGRAM_PAGE_PATH, false);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            ;
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }
}
