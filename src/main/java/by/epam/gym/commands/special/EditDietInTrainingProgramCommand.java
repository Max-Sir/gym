package by.epam.gym.commands.special;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.TrainingProgram;
import by.epam.gym.servlet.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Command to edit diet in training program.
 *
 * @author Eugene Makarenko
 * @see ActionCommand
 * @see by.epam.gym.entities.TrainingProgram
 */
public class EditDietInTrainingProgramCommand implements ActionCommand {

    /**
     * Implementation of command to edit diet in training program.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        String diet = request.getParameter(DIET_PARAMETER);

        HttpSession session = request.getSession();
        TrainingProgram trainingProgram = (TrainingProgram) session.getAttribute(TRAINING_PROGRAM_ATTRIBUTE);
        trainingProgram.setDiet(diet);

        return new Page(Page.EDIT_TRAINING_PROGRAM_PAGE_PATH, false);
    }
}
