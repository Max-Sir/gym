package by.epam.gym.dao;

import by.epam.gym.entities.TrainingProgram;
import by.epam.gym.exceptions.DAOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that provide access to the database and deal with TrainingProgram entity.
 *
 * @author Eugene Makarenko
 * @see AbstractDAOImpl
 * @see TrainingProgramDAOImpl
 */
public class TrainingProgramDAOImpl extends AbstractDAOImpl<TrainingProgram> {

    private static final String AUTHOR_ID_COLUMN_LABEL = "author_id";
    private static final String PERSONAL_TRAINER_ID_COLUMN_LABEL = "personal_trainer_id";
    private static final String CLIENT_ID_COLUMN_LABEL = "client_id";
    private static final String START_DATE_COLUMN_LABEL = "start_date";
    private static final String END_DATE_COLUMN_LABEL = "end_date";
    private static final String DIET_COLUMN_LABEL = "diet";

    private static final String TRAINING_PROGRAMS_RESOURCES_FILE_NAME = "training_programs";

    /**
     * Instantiates a new UserDAOImpl.
     *
     * @param connection   the connection to database.
     */
    public TrainingProgramDAOImpl(Connection connection) {
        super(connection, TRAINING_PROGRAMS_RESOURCES_FILE_NAME);
    }

    /**
     * This method builds TrainingProgram object from ResultSet object.
     *
     * @param resultSet the result set of statement.
     * @return The TrainingProgram object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public TrainingProgram buildEntity(ResultSet resultSet) throws DAOException {
        try {
            TrainingProgram trainingProgram = new TrainingProgram();

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            int authorId = resultSet.getInt(AUTHOR_ID_COLUMN_LABEL);
            int personalTrainerId = resultSet.getInt(PERSONAL_TRAINER_ID_COLUMN_LABEL);
            int clientId = resultSet.getInt(CLIENT_ID_COLUMN_LABEL);
            Date startDate = resultSet.getDate(START_DATE_COLUMN_LABEL);
            Date endDate = resultSet.getDate(END_DATE_COLUMN_LABEL);
            String diet = resultSet.getString(DIET_COLUMN_LABEL);

            trainingProgram.setId(id);
            trainingProgram.setAuthorId(authorId);
            trainingProgram.setPersonalTrainerId(personalTrainerId);
            trainingProgram.setClientId(clientId);
            trainingProgram.setStart(startDate);
            trainingProgram.setEnd(endDate);
            trainingProgram.setDiet(diet);

            return trainingProgram;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }
}
