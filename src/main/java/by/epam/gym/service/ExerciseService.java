package by.epam.gym.service;

import by.epam.gym.dao.ExerciseDAOImpl;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.exceptions.ServiceException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * This method adds exercise in database.
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
     * @throws ServiceException object if execution of method is failed.
     */
    public Map<Integer, List<Exercise>> showExerciseFromTrainingProgram(int trainingProgramId) throws ServiceException {
        try(ConnectionManager<ExerciseDAOImpl> connectionManager = new ConnectionManager<>(ExerciseDAOImpl.class)) {
            ExerciseDAOImpl exerciseDAO = connectionManager.createDAO();

            return exerciseDAO.showExerciseFromTrainingProgram(trainingProgramId);
        }  catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * This method finds all exercises id and name.
     *
     * @return Map with ids and names.
     * @throws ServiceException object if execution of method is failed.
     */
    public Map<Integer, String> findAllExercisesIdAndName() throws ServiceException {
        try(ConnectionManager<ExerciseDAOImpl> connectionManager = new ConnectionManager<>(ExerciseDAOImpl.class)) {
            ExerciseDAOImpl exerciseDAO = connectionManager.createDAO();

            return exerciseDAO.findAllExercisesIdAndName();
        }  catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * This method adds exercise to training program.
     *
     * @param trainingProgramId the training program id.
     * @param daysAndExercises the Map with day numbers and exercises.
     * @return true if operation was made successfully and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean addExercisesToTrainingProgram(int trainingProgramId, Map<Integer, List<Exercise>> daysAndExercises) throws ServiceException {

        ConnectionManager<ExerciseDAOImpl> connectionManager = null;
        try {
            connectionManager = new ConnectionManager<>(ExerciseDAOImpl.class);
            connectionManager.startTransaction();
            ExerciseDAOImpl exerciseDAO = connectionManager.createDAO();

            Set<Map.Entry<Integer, List<Exercise>>> entrySet = daysAndExercises.entrySet();
            for (Map.Entry<Integer, List<Exercise>> entry : entrySet) {
                int dayNumber = entry.getKey();
                List<Exercise> exercises = entry.getValue();
                for (Exercise exercise : exercises) {
                    int setsCount = exercise.getSetsCount();
                    int repeatsCount = exercise.getRepeatsCount();
                    int numberOfExecution = exercises.indexOf(exercise) + 1;
                    int exerciseId = exercise.getId();

                   boolean isResultSuccessful = exerciseDAO.addExerciseToTrainingProgram(trainingProgramId,exerciseId,dayNumber,setsCount,repeatsCount,numberOfExecution);
                   if (!isResultSuccessful){
                       connectionManager.rollbackTransaction();
                       return false;
                   }
                }
            }

            connectionManager.commitTransaction();
            return true;
        }catch (Exception exception) {
            if (connectionManager != null){
                connectionManager.rollbackTransaction();
            }
            throw new ServiceException("Exception detected. " + exception);
        } finally {
            if (connectionManager != null){
                connectionManager.endTransaction();
                connectionManager.close();
            }
        }
    }

    /**
     * This methods finds exercise by id.
     *
     * @param id the exercise id.
     * @return the Exercise object.
     * @throws ServiceException object if execution of method is failed.
     */
    public Exercise findExerciseById(int id) throws ServiceException {
        try(ConnectionManager<ExerciseDAOImpl> connectionManager = new ConnectionManager<>(ExerciseDAOImpl.class)) {
            ExerciseDAOImpl exerciseDAO = connectionManager.createDAO();

            return exerciseDAO.findEntityById(id);
        }catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * This method deletes exercise from training program.
     *
     * @param trainingProgramId the training program id.
     * @param exerciseId the exercise id.
     * @param dayNumber the day number.
     * @return true if operation was made successfully and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean deleteExerciseFromTrainingProgram(int trainingProgramId, int exerciseId, int dayNumber) throws ServiceException {
        try(ConnectionManager<ExerciseDAOImpl> connectionManager = new ConnectionManager<>(ExerciseDAOImpl.class)){
            ExerciseDAOImpl exerciseDAO = connectionManager.createDAO();

            return exerciseDAO.deleteExerciseFromTrainingProgram(trainingProgramId,exerciseId,dayNumber);
        }catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }
}
