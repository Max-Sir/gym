package by.epam.gym.dao;

import by.epam.gym.entities.TrainingProgram;
import by.epam.gym.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that provide access to the database and deal with TrainingProgram entity.
 *
 * @author Eugene Makarenko
 * @see AbstractDAOImpl
 * @see TrainingProgramDAOImpl
 */
public class TrainingProgramDAOImpl extends AbstractDAOImpl<TrainingProgram> {

    /**
     * Common queries.
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM training_programs";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM training_programs WHERE id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM training_programs WHERE id=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO training_programs (author_id, personal_trainer_id, client_id, start_date, end_date, diet) VALUES(?,?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE training_programs SET author_id=?, personal_trainer_id=?, client_id=?, start_date=?, end_date=?, diet=? WHERE id=?";

    private static final String SELECT_IS_PERSONAL_TRAINER_NEED_VALUE_QUERY = "SELECT is_personal_trainer_need FROM orders WHERE client_id=?";
    private static final String SELECT_CLIENT_TRAINING_PROGRAM_QUERY = "SELECT * FROM training_programs WHERE id IN (SELECT MAX(id) FROM training_programs WHERE client_id=?)";
    private static final String SELECT_LAST_INSERT_ID_QUERY = "SELECT LAST_INSERT_ID()";
    private static final String DELETE_EXERCISES_FROM_TRAINING_PROGRAM_QUERY = "DELETE FROM training_complexes WHERE program_id=?";

    private static final String AUTHOR_ID_COLUMN_LABEL = "author_id";
    private static final String PERSONAL_TRAINER_ID_COLUMN_LABEL = "personal_trainer_id";
    private static final String CLIENT_ID_COLUMN_LABEL = "client_id";
    private static final String START_DATE_COLUMN_LABEL = "start_date";
    private static final String END_DATE_COLUMN_LABEL = "end_date";
    private static final String DIET_COLUMN_LABEL = "diet";
    private static final String LAST_INSERT_ID_COLUMN_LABEL = "LAST_INSERT_ID()";
    private static final String IS_PERSONAL_TRAINER_NEED_COLUMN_LABEL = "is_personal_trainer_need";

    /**
     * Instantiates a new TrainingProgramDAOImpl.
     *
     * @param connection the connection to database.
     */
    public TrainingProgramDAOImpl(Connection connection) {
        super(connection);
    }

    /**
     * This method selects client's training program.
     *
     * @param clientId the client's id.
     * @return Training program object.
     * @throws DAOException object if execution of query is failed.
     */
    public TrainingProgram selectClientTrainingProgram(int clientId) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_CLIENT_TRAINING_PROGRAM_QUERY, clientId)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            TrainingProgram trainingProgram = null;
            if (resultSet.next()) {
                trainingProgram = buildEntity(resultSet);
            }

            return trainingProgram;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method inserts TrainingProgram object and selects its id.
     *
     * @param trainingProgram the training program.
     * @return training program's id.
     * @throws DAOException object if execution of query is failed.
     */
    public int insertTrainingProgram(TrainingProgram trainingProgram) throws DAOException {
        int lastId = 0;

        boolean isOperationSuccessful = insert(trainingProgram);
        if (isOperationSuccessful) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SELECT_LAST_INSERT_ID_QUERY);
                if (resultSet.next()) {
                    lastId = resultSet.getInt(LAST_INSERT_ID_COLUMN_LABEL);
                }
            } catch (SQLException exception) {
                throw new DAOException(exception.getMessage(), exception);
            }
        }
        return lastId;
    }

    /**
     * This method deletes exercises from training program.
     *
     * @param trainingProgramId the training program id.
     * @return true if operation was successful and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean deleteExercisesFromTrainingProgram(int trainingProgramId) throws DAOException {
        return executeQuery(DELETE_EXERCISES_FROM_TRAINING_PROGRAM_QUERY, trainingProgramId);
    }

    /**
     * This method select isPersonalTrainerNeed value for client.
     *
     * @param clientId the client's id.
     * @return true if client needs personal trainer and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean isClientNeedPersonalTrainer(int clientId) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_IS_PERSONAL_TRAINER_NEED_VALUE_QUERY, clientId)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int value = resultSet.getInt(IS_PERSONAL_TRAINER_NEED_COLUMN_LABEL);

                if (value == 1) {
                    return true;
                } else if (value == 0) {
                    return false;
                } else {
                    throw new DAOException(String.format("Unexpected result from query - %s.", SELECT_IS_PERSONAL_TRAINER_NEED_VALUE_QUERY));
                }
            } else {
                throw new DAOException(String.format("Unexpected result from query - %s.", SELECT_IS_PERSONAL_TRAINER_NEED_VALUE_QUERY));
            }

        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method gets entity's parameters.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    @Override
    protected List<String> getEntityParameters(TrainingProgram entity) {
        List<String> parameters = new ArrayList<>();

        int authorId = entity.getAuthorId();
        String authorIdValue = String.valueOf(authorId);
        parameters.add(authorIdValue);

        Integer personalTrainerId = entity.getPersonalTrainerId();
        if (personalTrainerId == null) {
            parameters.add(NULL_PARAMETER);
        } else {
            String personalTrainerIdValue = String.valueOf(personalTrainerId);
            parameters.add(personalTrainerIdValue);
        }

        int clientId = entity.getClientId();
        String clientIdValue = String.valueOf(clientId);
        parameters.add(clientIdValue);

        Date startDate = entity.getStartDate();
        String startDateValue = String.valueOf(startDate);
        parameters.add(startDateValue);

        Date endDate = entity.getEndDate();
        String endDateValue = String.valueOf(endDate);
        parameters.add(endDateValue);

        String diet = entity.getDiet();
        parameters.add(diet);

        return parameters;
    }

    /**
     * This method builds TrainingProgram object from ResultSet object.
     *
     * @param resultSet the result set of statement.
     * @return The TrainingProgram object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    protected TrainingProgram buildEntity(ResultSet resultSet) throws DAOException {
        try {
            TrainingProgram trainingProgram = new TrainingProgram();

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            trainingProgram.setId(id);

            int authorId = resultSet.getInt(AUTHOR_ID_COLUMN_LABEL);
            trainingProgram.setAuthorId(authorId);

            Integer personalTrainerId = resultSet.getInt(PERSONAL_TRAINER_ID_COLUMN_LABEL);
            if (personalTrainerId == 0) {
                personalTrainerId = null;
            }
            trainingProgram.setPersonalTrainerId(personalTrainerId);

            int clientId = resultSet.getInt(CLIENT_ID_COLUMN_LABEL);
            trainingProgram.setClientId(clientId);

            Date startDate = resultSet.getDate(START_DATE_COLUMN_LABEL);
            trainingProgram.setStartDate(startDate);

            Date endDate = resultSet.getDate(END_DATE_COLUMN_LABEL);
            trainingProgram.setEndDate(endDate);

            String diet = resultSet.getString(DIET_COLUMN_LABEL);
            trainingProgram.setDiet(diet);

            return trainingProgram;
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
