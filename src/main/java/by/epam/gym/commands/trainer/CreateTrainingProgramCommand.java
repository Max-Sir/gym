package by.epam.gym.commands.trainer;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.TrainingProgram;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.TrainingProgramService;
import by.epam.gym.service.UserService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.epam.gym.utils.ConfigurationManager.*;
import static by.epam.gym.utils.MessageManager.ADD_EXERCISE_FAILED_MESSAGE_PATH;
import static by.epam.gym.utils.MessageManager.RESULT_ATTRIBUTE;


/**
 * Command to create training program.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.TrainingProgram
 * @see ActionCommand
 */
public class CreateTrainingProgramCommand implements ActionCommand {

    private static final String USER_ATTRIBUTE = "user";
    private static final String DAYS_ATTRIBUTE = "days";

    private static final String DATE_START_PARAMETER = "startDate";
    private static final String DATE_END_PARAMETER = "endDate";
    private static final String DAYS_COUNT_PARAMETER = "daysCount";
    private static final String DIET_PARAMETER = "diet";
    private static final String CLIENT_ID_PARAMETER = "clientId";
    /**
     * Implementation of command to create training program in database.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl;

        try{
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            int authorId = user.getId();

            String clientIdValue = request.getParameter(CLIENT_ID_PARAMETER);
            int clientId = Integer.parseInt(clientIdValue);

            Integer personalTrainerId = null;
            UserService userService = new UserService();
            boolean isPersonalClientNeed = userService.isClientNeedPersonalTrainer(clientId);
            if (isPersonalClientNeed){
                personalTrainerId = authorId;
            }

            String startDateValue = request.getParameter(DATE_START_PARAMETER);
            Date startDate = Date.valueOf(startDateValue);

            String endDateVale = request.getParameter(DATE_END_PARAMETER);
            Date endDate = Date.valueOf(endDateVale);

            String diet = request.getParameter(DIET_PARAMETER);

            TrainingProgram trainingProgram = new TrainingProgram();
            trainingProgram.setAuthorId(authorId);
            trainingProgram.setClientId(clientId);
            trainingProgram.setPersonalTrainerId(personalTrainerId);
            trainingProgram.setDiet(diet);
            trainingProgram.setStart(startDate);
            trainingProgram.setEnd(endDate);

            TrainingProgramService trainingProgramService = new TrainingProgramService();
            boolean isOperationSuccessful = trainingProgramService.createTrainingProgram(trainingProgram);

            if (isOperationSuccessful) {
                String daysCountValue = request.getParameter(DAYS_COUNT_PARAMETER);
                int daysCount = Integer.parseInt(daysCountValue);

                Map<Integer, List<Exercise>> days = new HashMap<>();
                for (int index = 1; index <= daysCount; index++) {
                    List<Exercise> list = new ArrayList<>();
                    days.put(index, list);
                }

                session.setAttribute(DAYS_ATTRIBUTE, days);
                pageUrl = ConfigurationManager.getProperty(SUCCESSFUL_TRAINING_PROGRAM_CREATION_PAGE_PATH);
            } else {
                request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(ADD_EXERCISE_FAILED_MESSAGE_PATH));
                pageUrl = ConfigurationManager.getProperty(TRAINER_PAGE_PATH);
            }

            page.setRedirect(false);

        }catch (ServiceException exception) {
            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }

        page.setPageUrl(pageUrl);
        return page;
    }
}
