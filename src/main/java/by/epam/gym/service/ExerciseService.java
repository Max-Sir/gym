package by.epam.gym.service;

import by.epam.gym.dao.ConnectionManager;
import by.epam.gym.dao.ExerciseDAOImpl;
import by.epam.gym.dao.TrainingProgramDAOImpl;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.entities.exercise.ExerciseDifficultyLevel;
import by.epam.gym.exceptions.ConnectionException;
import by.epam.gym.exceptions.DAOException;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.utils.ExerciseDataValidator;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Service class for Exercise entity.
 *
 * @author Eugene Makarenko
 * @see Exercise
 * @see ExerciseDAOImpl
 * @see ConnectionManager
 */
public class ExerciseService {

    private static final Logger LOGGER = Logger.getLogger(ExerciseService.class);

    private static final int DAY_NUMBER_INCREMENT_INDEX = 1;

    /**
     * This method adds exercise to training program.
     *
     * @param trainingProgramId the training program id.
     * @param daysAndExercises  the Map with day numbers and exercises.
     * @return true if operation was made successfully and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean addExercisesToTrainingProgram(int trainingProgramId, Map<Integer, List<Exercise>> daysAndExercises, boolean isCleanNeed) throws ServiceException {

        ConnectionManager connectionManager = null;
        try {
            connectionManager = new ConnectionManager();
            connectionManager.startTransaction();

            if (isCleanNeed) {
                TrainingProgramDAOImpl trainingProgramDAO = new TrainingProgramDAOImpl(connectionManager.getConnection());
                boolean isCleanOperationSuccessful = trainingProgramDAO.deleteExercisesFromTrainingProgram(trainingProgramId);

                if (!isCleanOperationSuccessful) {
                    return false;
                }
            }

            ExerciseDAOImpl exerciseDAO = new ExerciseDAOImpl(connectionManager.getConnection());

            Set<Map.Entry<Integer, List<Exercise>>> entrySet = daysAndExercises.entrySet();
            for (Map.Entry<Integer, List<Exercise>> entry : entrySet) {
                int dayNumber = entry.getKey();
                List<Exercise> exercises = entry.getValue();
                for (Exercise exercise : exercises) {
                    int setsCount = exercise.getSetsCount();
                    int repeatsCount = exercise.getRepeatsCount();
                    int numberOfExecution = exercises.indexOf(exercise) + DAY_NUMBER_INCREMENT_INDEX;
                    int exerciseId = exercise.getId();

                    boolean isResultSuccessful = exerciseDAO.insertExerciseIntoTrainingProgram(trainingProgramId, exerciseId, dayNumber, setsCount, repeatsCount, numberOfExecution);
                    if (!isResultSuccessful) {
                        connectionManager.rollbackTransaction();
                        return false;
                    }
                }
            }

            connectionManager.commitTransaction();
            return true;
        } catch (ConnectionException | DAOException exception) {
            LOGGER.warn("Exception during <add exercise to training program> operation.");
            if (connectionManager != null) {
                connectionManager.rollbackTransaction();
            }
            throw new ServiceException("Exception detected. " + exception);
        } finally {
            if (connectionManager != null) {
                connectionManager.endTransaction();
                connectionManager.close();
            }
        }
    }

    /**
     * This method finds all exercises id and name.
     *
     * @return List with exercises.
     * @throws ServiceException object if execution of method is failed.
     */
    public List<Exercise> findAllExercisesIdAndName() throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            ExerciseDAOImpl exerciseDAO = new ExerciseDAOImpl(connectionManager.getConnection());
            List<Exercise> exercises = exerciseDAO.selectAll();

            return exercises;
        } catch (ConnectionException | DAOException exception) {
            LOGGER.warn("Exception during <find all exercises id and name> operation.");
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * This method adds exercise in database.
     *
     * @param exercise the exercise.
     * @return true if operation successful and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean saveExercise(Exercise exercise) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            ExerciseDAOImpl exerciseDAO = new ExerciseDAOImpl(connectionManager.getConnection());

            return exerciseDAO.insert(exercise);
        } catch (ConnectionException | DAOException exception) {
            LOGGER.warn("Exception during <create exercise> operation.");
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * This method creates Exercise object.
     *
     * @param name        the exercise's name.
     * @param levelValue  the exercise's difficulty level value.
     * @param description the exercise's description.
     * @return Exercise object.
     */
    public Exercise createExercise(String name, String levelValue, String description) {
        Exercise exercise = new Exercise();
        exercise.setName(name);

        ExerciseDifficultyLevel level = ExerciseDifficultyLevel.valueOf(levelValue);
        exercise.setLevel(level);
        exercise.setDescription(description);

        return exercise;
    }

    /**
     * This method edits exercise in training program.
     *
     * @param exerciseIdValue   the exercise's id value.
     * @param dayNumberValue    the exercise's day number value.
     * @param setsCountValue    the exercise's sets count value.
     * @param repeatsCountValue the exercise's repeats count value.
     * @param daysAndExercises  the days and exercises in this day.
     * @return true if operation was successful and false otherwise.
     */
    public boolean editExercise(String exerciseIdValue, String dayNumberValue, String setsCountValue, String repeatsCountValue, Map<Integer, List<Exercise>> daysAndExercises) {
        int setsCount = Integer.parseInt(setsCountValue);
        int repeatsCount = Integer.parseInt(repeatsCountValue);
        ExerciseDataValidator exerciseDataValidator = new ExerciseDataValidator();
        boolean isDataValid = exerciseDataValidator.checkExerciseDuringEditOperation(setsCount, repeatsCount);
        if (!isDataValid) {
            return false;
        }

        int exerciseId = Integer.parseInt(exerciseIdValue);
        int dayNumber = Integer.parseInt(dayNumberValue);
        Set<Map.Entry<Integer, List<Exercise>>> entrySet = daysAndExercises.entrySet();
        for (Map.Entry<Integer, List<Exercise>> entry : entrySet) {
            int day = entry.getKey();
            if (day == dayNumber) {
                List<Exercise> exercises = entry.getValue();
                Iterator<Exercise> iterator = exercises.iterator();
                while (iterator.hasNext()) {
                    Exercise exercise = iterator.next();
                    int currentExerciseId = exercise.getId();

                    if (exerciseId == currentExerciseId) {
                        exercise.setSetsCount(setsCount);
                        exercise.setRepeatsCount(repeatsCount);
                    }
                }
            }
        }

        return true;
    }

    /**
     * This method deletes exercise from training program.
     *
     * @param exerciseIdValue  the exercise's id value.
     * @param dayNumberValue   the exercise's day number value.
     * @param daysAndExercises the days and exercises in this day.
     */
    public void deleteExerciseFromTrainingProgram(String exerciseIdValue, String dayNumberValue, TreeMap<Integer, List<Exercise>> daysAndExercises) {
        int exerciseId = Integer.parseInt(exerciseIdValue);
        int dayNumber = Integer.parseInt(dayNumberValue);

        Set<Map.Entry<Integer, List<Exercise>>> entrySet = daysAndExercises.entrySet();
        for (Map.Entry<Integer, List<Exercise>> entry : entrySet) {
            int day = entry.getKey();
            if (day == dayNumber) {
                List<Exercise> exercises = entry.getValue();
                Iterator<Exercise> iterator = exercises.iterator();
                while (iterator.hasNext()) {
                    Exercise exercise = iterator.next();
                    int currentExerciseId = exercise.getId();

                    if (exerciseId == currentExerciseId) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    /**
     * This method adds exercise in training program.
     *
     * @param exerciseIdValue   the exercise's id value.
     * @param dayNumberValue    the exercise's day number value.
     * @param setsCountValue    the exercise's sets count value.
     * @param repeatsCountValue the exercise's repeats count value.
     * @param daysAndExercises  the days and exercises in this day.
     * @return true if operation was successful and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean addExerciseInTrainingProgram(String exerciseIdValue, String dayNumberValue, String setsCountValue, String repeatsCountValue, TreeMap<Integer, List<Exercise>> daysAndExercises) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            int dayNumber = Integer.parseInt(dayNumberValue);
            List<Exercise> exercises = daysAndExercises.get(dayNumber);
            int exerciseId = Integer.parseInt(exerciseIdValue);
            ExerciseDataValidator exerciseDataValidator = new ExerciseDataValidator();
            boolean isExerciseUnique = exerciseDataValidator.checkExerciseForUniqueInTrainingProgram(exerciseId, exercises);
            if (!isExerciseUnique) {
                return false;
            }

            int setsCount = Integer.parseInt(setsCountValue);
            int repeatsCount = Integer.parseInt(repeatsCountValue);
            boolean isDataValid = exerciseDataValidator.checkExerciseCountDuringAddOperation(exercises, setsCount, repeatsCount);
            if (!isDataValid) {
                return false;
            }

            ExerciseDAOImpl exerciseDAO = new ExerciseDAOImpl(connectionManager.getConnection());
            Exercise exercise = exerciseDAO.selectEntityById(exerciseId);
            exercise.setSetsCount(setsCount);
            exercise.setRepeatsCount(repeatsCount);
            exercises.add(exercise);

            return true;
        } catch (ConnectionException | DAOException exception) {
            LOGGER.warn("Exception during <add exercise in training program> operation.");
            throw new ServiceException("Exception detected. " + exception);
        }
    }
}
