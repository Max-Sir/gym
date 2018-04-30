package by.epam.gym.commands.special;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.TrainingProgram;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.TrainingProgramService;
import by.epam.gym.servlet.Page;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.TreeMap;

import static by.epam.gym.servlet.Page.DESCRIBE_TRAINING_PROGRAM_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.INFORMATION_NOT_FOUND_MESSAGE_KEY;

/**
 * Command to show client's training program.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.TrainingProgram
 * @see by.epam.gym.service.TrainingProgramService
 * @see ActionCommand
 */
public class ShowClientTrainingProgramCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(ShowClientTrainingProgramCommand.class);

    /**
     * Implementation of commands to show client's training program.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {

        try {
            String clientIdValue = request.getParameter(CLIENT_ID_PARAMETER);
            int clientId = Integer.parseInt(clientIdValue);
            TrainingProgramService trainingProgramService = new TrainingProgramService();
            TrainingProgram trainingProgram = trainingProgramService.findTrainingProgramById(clientId);

            if (trainingProgram == null) {
                return new Page(Page.MAIN_PAGE_PATH, false, INFORMATION_NOT_FOUND_MESSAGE_KEY);
            }

            HttpSession session = request.getSession();
            session.setAttribute(TRAINING_PROGRAM_ATTRIBUTE, trainingProgram);

            int trainingProgramId = trainingProgram.getId();
            String authorName = trainingProgramService.findTrainingProgramAuthorName(trainingProgramId);
            session.setAttribute(NAME_ATTRIBUTE, authorName);

            TreeMap<Integer, List<Exercise>> daysAndExercises = trainingProgramService.showExerciseFromTrainingProgram(trainingProgramId);
            session.setAttribute(DAYS_AND_EXERCISES_ATTRIBUTE, daysAndExercises);

            LOGGER.info(String.format("Data was loaded successful for training program of client id - %d.", clientId));
            return new Page(DESCRIBE_TRAINING_PROGRAM_PAGE_PATH, false);
        } catch (ServiceException exception) {
            LOGGER.error(String.format("Service exception detected in command - %s. ", getClass().getSimpleName()), exception);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }
}
