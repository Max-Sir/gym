package by.epam.gym.dao;

import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.entities.exercise.ExerciseDifficultyLevel;
import by.epam.gym.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that provide access to the database and deal with Exercise entity.
 *
 * @author Eugene Makarenko
 * @see AbstractDAOImpl
 * @see Exercise
 */
public class ExerciseDAOImpl extends AbstractDAOImpl<Exercise> {

    private static final String NAME_COLUMN_LABEL = "name";
    private static final String LEVEL_COLUMN_LABEL = "level";
    private static final String DESCRIPTION_COLUMN_LABEL = "description";
    private static final String SETS_COUNT_COLUMN_LABEL = "sets_count";
    private static final String REPEATS_COUNT_COLUMN_LABEL = "repeats_count";
    private static final String DAY_NUMBER_COLUMN_LABEL = "day_number";
    private static final String EXECUTION_NUMBER_COLUMN_LABEL = "execution_number";

    private static final String EXERCISES_RESOURCES_FILE_NAME = "exercises";

    private static final int SUCCESS_RESULT = 1;

    /**
     * Instantiates a new UserDAOImpl.
     *
     * @param connection the connection to database.
     */
    public ExerciseDAOImpl(Connection connection) {
        super(connection, EXERCISES_RESOURCES_FILE_NAME);
    }

    /**
     * This method finds all exercises from training program.
     *
     * @param trainingProgramId the training program id.
     * @return Map with the day number and exercises.
     * @throws DAOException object if execution of query is failed.
     */
    public Map<Integer, List<Exercise>> showExerciseFromTrainingProgram(int trainingProgramId) throws DAOException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("query.get_exercises_from_training_program"))) {
            preparedStatement.setInt(1,trainingProgramId);

            Map<Integer, List<Exercise>> exercisesByDays = new HashMap<>();

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Exercise> exercisesByDay = new ArrayList<>();
            int dayIndex = 1;

            while (resultSet.next()){

                int dayNumber = resultSet.getInt(DAY_NUMBER_COLUMN_LABEL);
                int setsCount = resultSet.getInt(SETS_COUNT_COLUMN_LABEL);
                int repeatsCount = resultSet.getInt(REPEATS_COUNT_COLUMN_LABEL);
                int executionNumber = resultSet.getInt(EXECUTION_NUMBER_COLUMN_LABEL);

                if (dayIndex != dayNumber){
                    exercisesByDays.put(dayIndex, exercisesByDay);
                    exercisesByDay = new ArrayList<>();
                    dayIndex = dayNumber;
                }

                Exercise exercise = buildEntity(resultSet);
                exercise.setDayNumber(dayNumber);
                exercise.setRepeatsCount(repeatsCount);
                exercise.setSetsCount(setsCount);
                exercise.setExecutionNumber(executionNumber);

                exercisesByDay.add(exercise);
            }

            exercisesByDays.put(dayIndex,exercisesByDay);

            return exercisesByDays;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * This method finds all exercises id and name.
     *
     * @return Map with ids and names.
     * @throws DAOException object if execution of query is failed.
     */
    public Map<Integer, String> findAllExercisesIdAndName() throws DAOException {
        String sqlQuery = resourceBundle.getString("query.get_id_and_name_all_exercises");
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Integer, String> exercisesIdAndName = new HashMap<>();

            while (resultSet.next()){
                int id = resultSet.getInt(ID_COLUMN_LABEL);
                String name = resultSet.getString(NAME_COLUMN_LABEL);

                exercisesIdAndName.put(id,name);
            }

            return exercisesIdAndName;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * This method adds exercise to training program.
     *
     * @param trainingProgramId the training program id.
     * @param exerciseId the exercise id.
     * @param dayNumber the day number.
     * @param setsCount the sets count.
     * @param repeatsCount the repeats count.
     * @param numberOfExecution the number of execution.
     * @return true if operation was made successfully and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean addExerciseToTrainingProgram(int trainingProgramId, int exerciseId, int dayNumber, int setsCount, int repeatsCount, int numberOfExecution) throws DAOException {
        String sqlQuery = resourceBundle.getString("query.add_exercise_to_program");
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1,trainingProgramId);
            preparedStatement.setInt(2,exerciseId);
            preparedStatement.setInt(3,dayNumber);
            preparedStatement.setInt(4,setsCount);
            preparedStatement.setInt(5,repeatsCount);
            preparedStatement.setInt(6,numberOfExecution);

            int result = preparedStatement.executeUpdate();

            return result == SUCCESS_RESULT;
        }catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    public boolean cleanTrainingProgramFromExercises(int programId) throws DAOException {
        String sqlQuery = resourceBundle.getString("query.clean_training_program");
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
            preparedStatement.setInt(1,programId);

            int result = preparedStatement.executeUpdate();

            return result != 0;
        }catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }
    /**
     * This method builds Exercise object from ResultSet object.
     *
     * @param resultSet the result set of statement.
     * @return The Exercise object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public Exercise buildEntity(ResultSet resultSet) throws DAOException {
        try {
            Exercise exercise = new Exercise();

            String levelValue = resultSet.getString(LEVEL_COLUMN_LABEL);

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            String name = resultSet.getString(NAME_COLUMN_LABEL);
            ExerciseDifficultyLevel level = ExerciseDifficultyLevel.valueOf(levelValue);
            String description = resultSet.getString(DESCRIPTION_COLUMN_LABEL);

            exercise.setId(id);
            exercise.setName(name);
            exercise.setLevel(level);
            exercise.setDescription(description);

            return exercise;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }
}
