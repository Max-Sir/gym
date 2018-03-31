package by.epam.gym.dao;

import by.epam.gym.entities.TrainingProgram;
import by.epam.gym.exceptions.DAOException;

import java.sql.*;

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
    private static final String MAX_ID_COLUMN_LABEL = "MAX(id)";

    private static final int EMPTY_TRAINING_PROGRAM_ID_VALUE = 0;

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
     * This method finds last training program of trainer.
     *
     * @param authorId the author id.
     * @return the id of last created training program.
     * @throws DAOException object if execution of query is failed.
     */
    public int findLastTrainingProgram(int authorId) throws DAOException {
        String sqlQuery = resourceBundle.getString("query.get_last_training_program_of_trainer");
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
            preparedStatement.setInt(1,authorId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                int id = resultSet.getInt(ID_COLUMN_LABEL);

                return id;
            } else {
                throw new DAOException(String.format("Unexpected result for query - %s.",sqlQuery));
            }

        }catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    public int findClientTrainingProgram(int clientId) throws DAOException {
        String sqlQuery = resourceBundle.getString("query.find_client_training_program");
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1,clientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                int trainingProgramId = resultSet.getInt(MAX_ID_COLUMN_LABEL);
                return trainingProgramId;
            } else {
                return EMPTY_TRAINING_PROGRAM_ID_VALUE;
            }
        }catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
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
