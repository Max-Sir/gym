package by.epam.gym.service;

import by.epam.gym.dao.ExerciseDAOImpl;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.exceptions.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Service class for Exercise entity.
 *
 * @author Eugene Makarenko
 * @see Exercise
 * @see ExerciseDAOImpl
 * @see ConnectionManager
 */
public class ExerciseService {

    /**
     * This metod adds exercise in database.
     *
     * @param exercise the exercise.
     * @throws ServiceException object if execution of method is failed.
     */
    public void addExerciseToDatabase(Exercise exercise) throws ServiceException {
        try(ConnectionManager<ExerciseDAOImpl> connectionManager = new ConnectionManager<>(ExerciseDAOImpl.class)) {
            ExerciseDAOImpl exerciseDAO = connectionManager.createDAO();

            exerciseDAO.insert(exercise);

        }  catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * This method shows exercises from training program.
     *
     * @param trainingProgramId the training program id.
     * @return List of exercises.
     */
    public Map<Integer, List<Exercise>> showExerciseFromTrainingProgram(int trainingProgramId) throws ServiceException {
        try(ConnectionManager<ExerciseDAOImpl> connectionManager = new ConnectionManager<>(ExerciseDAOImpl.class)) {
            ExerciseDAOImpl exerciseDAO = connectionManager.createDAO();

            return exerciseDAO.showExerciseFromTrainingProgram(trainingProgramId);
        }  catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    public Map<Integer, String> findAllExercisesIdAndName() throws ServiceException {
        try(ConnectionManager<ExerciseDAOImpl> connectionManager = new ConnectionManager<>(ExerciseDAOImpl.class)) {
            ExerciseDAOImpl exerciseDAO = connectionManager.createDAO();

            return exerciseDAO.findAllExercisesIdAndName();
        }  catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    public void addExerciseToProgram(int programId, int exerciseId, int dayNumber, int setsCount, int repeatsCount, int numberOfExecution) throws ServiceException {
        try(ConnectionManager<ExerciseDAOImpl> connectionManager = new ConnectionManager<>(ExerciseDAOImpl.class)) {
            ExerciseDAOImpl exerciseDAO = connectionManager.createDAO();

            exerciseDAO.addExerciseToProgram(programId,exerciseId,dayNumber,setsCount,repeatsCount,numberOfExecution);

        }catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }

    }

    public Exercise findExerciseById(int id) throws ServiceException {
        try(ConnectionManager<ExerciseDAOImpl> connectionManager = new ConnectionManager<>(ExerciseDAOImpl.class)) {
            ExerciseDAOImpl exerciseDAO = connectionManager.createDAO();

            return exerciseDAO.findEntityById(id);
        }catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }
}
