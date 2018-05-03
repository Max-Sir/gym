package by.epam.gym.dao;

import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.entities.exercise.ExerciseDifficultyLevel;
import by.epam.gym.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Class that provide access to the database and deal with Exercise entity.
 *
 * @author Eugene Makarenko
 * @see AbstractDAOImpl
 * @see Exercise
 */
public class ExerciseDAOImpl extends AbstractDAOImpl<Exercise> {

    /**
     * Common queries.
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM exercises";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM exercises WHERE id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM exercises WHERE id=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO exercises (name, level, description) VALUES(?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE exercises SET name=?, level=?, description=? WHERE id=?";

    private static final String SELECT_EXERCISE_FROM_TRAINING_PROGRAM_QUERY = "SELECT id, name, level, description, day_number, sets_count, repeats_count, execution_number" +
            " FROM exercises LEFT OUTER JOIN training_complexes  " +
            "ON exercises.id = training_complexes.exercise_id WHERE program_id=? ORDER BY day_number, execution_number ASC";

    private static final String INSERT_EXERCISE_INTO_TRAINING_PROGRAM = "INSERT INTO training_complexes " +
            "(program_id, exercise_id, day_number, sets_count, repeats_count, execution_number) VALUES (?,?,?,?,?,?)";

    private static final String NAME_COLUMN_LABEL = "name";
    private static final String LEVEL_COLUMN_LABEL = "level";
    private static final String DESCRIPTION_COLUMN_LABEL = "description";
    private static final String SETS_COUNT_COLUMN_LABEL = "sets_count";
    private static final String REPEATS_COUNT_COLUMN_LABEL = "repeats_count";
    private static final String DAY_NUMBER_COLUMN_LABEL = "day_number";
    private static final String EXECUTION_NUMBER_COLUMN_LABEL = "execution_number";

    /**
     * Instantiates a new AbstractDAOImpl.
     *
     * @param connection the connection to database.
     */
    public ExerciseDAOImpl(Connection connection) {
        super(connection);
    }

    /**
     * This method select exercises from training program.
     *
     * @param trainingProgramId the training program id.
     * @return TreeMap with the day number and exercises.
     * @throws DAOException object if execution of query is failed.
     */
    public TreeMap<Integer, List<Exercise>> selectExerciseFromTrainingProgram(int trainingProgramId) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_EXERCISE_FROM_TRAINING_PROGRAM_QUERY, trainingProgramId)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            TreeMap<Integer, List<Exercise>> exercisesByDays = new TreeMap<>();
            List<Exercise> exercisesByDay = new ArrayList<>();
            int dayIndex = 1;

            while (resultSet.next()) {
                int dayNumber = resultSet.getInt(DAY_NUMBER_COLUMN_LABEL);
                int setsCount = resultSet.getInt(SETS_COUNT_COLUMN_LABEL);
                int repeatsCount = resultSet.getInt(REPEATS_COUNT_COLUMN_LABEL);
                int executionNumber = resultSet.getInt(EXECUTION_NUMBER_COLUMN_LABEL);

                if (dayIndex != dayNumber) {
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

            exercisesByDays.put(dayIndex, exercisesByDay);

            return exercisesByDays;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method insert exercise to training program.
     *
     * @param trainingProgramId the training program id.
     * @param exerciseId        the exercise id.
     * @param dayNumber         the day number.
     * @param setsCount         the sets count.
     * @param repeatsCount      the repeats count.
     * @param numberOfExecution the number of execution.
     * @return true if operation was made successfully and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean insertExerciseIntoTrainingProgram(int trainingProgramId, int exerciseId, int dayNumber, int setsCount, int repeatsCount, int numberOfExecution) throws DAOException {
        return executeQuery(INSERT_EXERCISE_INTO_TRAINING_PROGRAM, trainingProgramId, exerciseId, dayNumber, setsCount, repeatsCount, numberOfExecution);
    }

    /**
     * This method gets entity's parameters.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    @Override
    protected List<String> getEntityParameters(Exercise entity) {
        List<String> parameters = new ArrayList<>();

        String name = entity.getName();
        parameters.add(name);

        ExerciseDifficultyLevel level = entity.getLevel();
        String levelValue = String.valueOf(level);
        parameters.add(levelValue);

        String description = entity.getDescription();
        parameters.add(description);

        return parameters;
    }

    /**
     * This method builds Exercise object from ResultSet object.
     *
     * @param resultSet the result set of statement.
     * @return The Exercise object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    protected Exercise buildEntity(ResultSet resultSet) throws DAOException {
        try {
            Exercise exercise = new Exercise();

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            exercise.setId(id);

            String name = resultSet.getString(NAME_COLUMN_LABEL);
            exercise.setName(name);

            String levelValue = resultSet.getString(LEVEL_COLUMN_LABEL);
            ExerciseDifficultyLevel level = ExerciseDifficultyLevel.valueOf(levelValue);
            exercise.setLevel(level);

            String description = resultSet.getString(DESCRIPTION_COLUMN_LABEL);
            exercise.setDescription(description);

            return exercise;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method initialize queries for common operations.
     *
     * @return Map object with queries.
     */
    @Override
    protected Map<String, String> initializeCommonQueries() {
        Map<String, String> commonQueries = new HashMap<>();

        commonQueries.put(SELECT_ALL_QUERY_KEY, SELECT_ALL_QUERY);
        commonQueries.put(SELECT_BY_ID_QUERY_KEY, SELECT_BY_ID_QUERY);
        commonQueries.put(DELETE_BY_ID_QUERY_KEY, DELETE_BY_ID_QUERY);
        commonQueries.put(INSERT_ENTITY_QUERY_KEY, INSERT_ENTITY_QUERY);
        commonQueries.put(UPDATE_ENTITY_QUERY_KEY, UPDATE_ENTITY_QUERY);

        return commonQueries;
    }
}
