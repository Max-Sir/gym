package by.epam.gym.commands.common.training;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.TrainingProgram;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.ExerciseService;
import by.epam.gym.service.TrainingProgramService;
import by.epam.gym.service.UserService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import static by.epam.gym.utils.ConfigurationManager.DESCRIBE_TRAINING_PROGRAM_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;

/**
 * Command to describe training program.
 *
 *@author Eugene Makarenko
 */
public class DescribeTrainingProgramCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(DescribeTrainingProgramCommand.class);

    private static final String TRAINING_PROGRAM_ID_PARAMETER = "programId";

    private static final String DAYS_ATTRIBUTE = "days";
    private static final String START_DATE_ATTRIBUTE = "startDate";
    private static final String END_DATE_ATTRIBUTE = "endDate";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String DIET_ATTRIBUTE = "diet";

    /**
     * Implementation of command to describe training program.
     *
     * @param request HttpServletRequest object.
     * @return Page object.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl;

        String trainingProgramIdValue = request.getParameter(TRAINING_PROGRAM_ID_PARAMETER);
        int trainingProgramId = Integer.parseInt(trainingProgramIdValue);

        try {
            TrainingProgramService trainingProgramService = new TrainingProgramService();
            TrainingProgram trainingProgram = trainingProgramService.findTrainingProgramById(trainingProgramId);

            Date startDate = trainingProgram.getStart();
            Date endDate = trainingProgram.getEnd();
            String diet = trainingProgram.getDiet();

            request.setAttribute(START_DATE_ATTRIBUTE,startDate);
            request.setAttribute(END_DATE_ATTRIBUTE,endDate);
            request.setAttribute(DIET_ATTRIBUTE, diet);

            ExerciseService exerciseService = new ExerciseService();
            Map<Integer, List<Exercise>> exercisesPerDays = exerciseService.showExerciseFromTrainingProgram(trainingProgramId);

            request.setAttribute(DAYS_ATTRIBUTE, exercisesPerDays);

            UserService userService = new UserService();
            String name = userService.findTrainingProgramAuthorName(trainingProgramId);

            request.setAttribute(NAME_ATTRIBUTE, name);

            pageUrl = ConfigurationManager.getProperty(DESCRIBE_TRAINING_PROGRAM_PAGE_PATH);
            page.setRedirect(false);

        } catch (ServiceException exception) {
            LOGGER.warn(String.format("Can't load all data for training program id=%d. ", trainingProgramId), exception);

            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }
        page.setPageUrl(pageUrl);
        return page;
    }
}
