package by.epam.gym.commands.client;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.TrainingProgram;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.ExerciseService;
import by.epam.gym.service.TrainingProgramService;
import by.epam.gym.service.UserService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import static by.epam.gym.utils.ConfigurationManager.CLIENT_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.DESCRIBE_TRAINING_PROGRAM_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.NO_TRAINING_PROGRAM_MESSAGE_PATH;
import static by.epam.gym.utils.MessageManager.RESULT_ATTRIBUTE;

public class ShowTrainingProgramCommand implements ActionCommand {

    private static final String USER_ATTRIBUTE = "user";

    private static final String DAYS_ATTRIBUTE = "days";
    private static final String START_DATE_ATTRIBUTE = "startDate";
    private static final String END_DATE_ATTRIBUTE = "endDate";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String DIET_ATTRIBUTE = "diet";
    private static final String EXERCISES_ATTRIBUTE = "exercises";
    private static final String PROGRAM_ID_PARAMETER = "programId";

    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl;

        try{
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            int clientId = user.getId();

            TrainingProgramService trainingProgramService = new TrainingProgramService();
            int programId = trainingProgramService.findClientTrainingProgramId(clientId);

            if (programId != 0){
                trainingProgramService = new TrainingProgramService();
                TrainingProgram trainingProgram = trainingProgramService.findTrainingProgramById(programId);

                Date startDate = trainingProgram.getStart();
                Date endDate = trainingProgram.getEnd();
                String diet = trainingProgram.getDiet();

                request.setAttribute(START_DATE_ATTRIBUTE,startDate);
                request.setAttribute(END_DATE_ATTRIBUTE,endDate);
                request.setAttribute(DIET_ATTRIBUTE, diet);

                ExerciseService exerciseService = new ExerciseService();
                Map<Integer, List<Exercise>> exercisesPerDays = exerciseService.showExerciseFromTrainingProgram(programId);

                session.setAttribute(DAYS_ATTRIBUTE, exercisesPerDays);

                exerciseService = new ExerciseService();
                Map<Integer, String> exercisesIdAndName = exerciseService.findAllExercisesIdAndName();

                session.setAttribute(EXERCISES_ATTRIBUTE,exercisesIdAndName);
                session.setAttribute(PROGRAM_ID_PARAMETER, programId);

                UserService userService = new UserService();
                String name = userService.findTrainingProgramAuthorName(programId);

                request.setAttribute(NAME_ATTRIBUTE, name);
                pageUrl = ConfigurationManager.getProperty(DESCRIBE_TRAINING_PROGRAM_PAGE_PATH);
            } else {
                request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(NO_TRAINING_PROGRAM_MESSAGE_PATH));
                pageUrl = ConfigurationManager.getProperty(CLIENT_PAGE_PATH);
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
