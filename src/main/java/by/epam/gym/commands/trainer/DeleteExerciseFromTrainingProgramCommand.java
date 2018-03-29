package by.epam.gym.commands.trainer;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.servlet.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to delete exercise from training program.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.TrainingProgram
 * @see by.epam.gym.entities.exercise.Exercise
 * @see by.epam.gym.service.ExerciseService
 * @see ActionCommand
 */
public class DeleteExerciseFromTrainingProgramCommand implements ActionCommand {

    /**
     * Implementation of command to delete exercise from training program.
     *
     * @param request HttpServletRequest object.
     * @return redirect page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        return null;
    }
}
