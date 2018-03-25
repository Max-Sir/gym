package by.epam.gym.dao;

import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.entities.exercise.ExerciseDifficultyLevel;
import by.epam.gym.exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    private static final String EXERCISES_RESOURCES_FILE_NAME = "exercises";

    /**
     * Instantiates a new UserDAOImpl.
     *
     * @param connection   the connection to database.
     */
    public ExerciseDAOImpl(Connection connection) {
        super(connection, EXERCISES_RESOURCES_FILE_NAME);
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
