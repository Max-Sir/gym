package by.epam.gym.commands.client;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.TrainingProgramService;
import by.epam.gym.servlet.Page;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.gym.servlet.Page.DESCRIBE_TRAINING_PROGRAM_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.REFUSE_TRAINING_PROGRAM_FAILED_MESSAGE_KEY;
import static by.epam.gym.utils.MessageManager.REFUSE_TRAINING_PROGRAM_SUCCESS_MESSAGE_KEY;

/**
 * Command to refuse training program.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see by.epam.gym.entities.TrainingProgram
 */
public class RefuseTrainingProgramCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(RefuseTrainingProgramCommand.class);

    /**
     * Implementation of command to refuse training program.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {

        try {
            String trainingProgramIdValue = request.getParameter(TRAINING_PROGRAM_ID_PARAMETER);
            int trainingProgramId = Integer.parseInt(trainingProgramIdValue);
            TrainingProgramService trainingProgramService = new TrainingProgramService();
            boolean isOperationSuccessful = trainingProgramService.refuseTrainingProgram(trainingProgramId);
            if (!isOperationSuccessful) {
                LOGGER.info(String.format("Training program id=%d wasn't deleted.", trainingProgramId));
                return new Page(DESCRIBE_TRAINING_PROGRAM_PAGE_PATH, false, REFUSE_TRAINING_PROGRAM_FAILED_MESSAGE_KEY);
            }

            HttpSession session = request.getSession();
            session.setAttribute(IS_RECORD_INSERTED, true);
            session.removeAttribute(TRAINING_PROGRAM_ATTRIBUTE);

            LOGGER.info(String.format("Training program id=%d was deleted successful.", trainingProgramId));
            return new Page(Page.MAIN_PAGE_PATH, false, REFUSE_TRAINING_PROGRAM_SUCCESS_MESSAGE_KEY);
        } catch (ServiceException exception) {
            LOGGER.error(String.format("Service exception detected in command - %s. ", getClass().getSimpleName()), exception);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }
}
