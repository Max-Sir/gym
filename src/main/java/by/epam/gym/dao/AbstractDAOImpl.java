package by.epam.gym.dao;

import by.epam.gym.dao.processor.QueryProcessor;
import by.epam.gym.entities.Entity;
import by.epam.gym.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Abstract root class of DAO level that provide access to the database and deal with application entities.
 *
 * @param <T> the entity type.
 * @author Eugene Makarenko
 * @see Connection
 * @see Entity
 * @see QueryProcessor
 * @see DAOException
 */
public abstract class AbstractDAOImpl<T extends Entity> implements DAO<T> {

    public static final String ID_COLUMN_LABEL = "id";

    protected Connection connection;
    protected ResourceBundle resourceBundle;

    /**
     * Instantiates a new UserDAOImpl.
     *
     * @param connection   the connection to database.
     * @param resourceName the resource file name.
     */
    public AbstractDAOImpl(Connection connection, String resourceName) {
        this.connection = connection;
        this.resourceBundle = ResourceBundle.getBundle(resourceName);
    }

    /**
     * This method finds all entities.
     *
     * @return List of found objects.
     * @throws DAOException object if execution of query is failed.
     */
    public List<T> findAll() throws DAOException {
        String sqlQuery = resourceBundle.getString("query.find_all");

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            List<T> entities = new ArrayList<T>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T entity = buildEntity(resultSet);
                entities.add(entity);
            }

            return entities;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * This method finds entity from database by id.
     *
     * @param id the entity's id.
     * @return the entity.
     * @throws DAOException object if execution of query is failed.
     */
    public T findEntityById(int id) throws DAOException {
        String sqlQuery = resourceBundle.getString("query.find_by_id");

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, id);

            T entity = null;

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                entity = buildEntity(resultSet);
            }

            return entity;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * This method deletes entity from database by id.
     *
     * @param id entity id.
     * @throws DAOException object if execution of query is failed.
     */
    public void deleteById(int id) throws DAOException {
        String sqlQuery = resourceBundle.getString("query.delete_by_id");

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, id);

            int queryResult = preparedStatement.executeUpdate();
            if (queryResult != 1) {
                throw new DAOException("On delete modify more then 1 record: " + queryResult);
            }

        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * This method insert entity in database.
     *
     * @param entity the entity.
     * @throws DAOException object if execution of query is failed.
     */
    public void insert(T entity) throws DAOException {
        String sqlQuery = resourceBundle.getString("query.insert_entity");
        QueryProcessor<T> queryProcessor = new QueryProcessor<T>(sqlQuery, connection, entity);

        queryProcessor.processInsertQuery();
    }

    /**
     * This method update entity in database.
     *
     * @param entity the entity.
     * @throws DAOException object if execution of query is failed.
     */
    public void update(T entity) throws DAOException {
        String sqlQuery = resourceBundle.getString("query.update_entity");
        QueryProcessor<T> queryProcessor = new QueryProcessor<T>(sqlQuery, connection, entity);

        queryProcessor.processUpdateQuery();
    }

    /**
     * This method builds entity from ResultSet object.
     *
     * @param resultSet the result set of statement.
     * @return the entity.
     * @throws DAOException object if execution of query is failed.
     */
    public abstract T buildEntity(ResultSet resultSet) throws DAOException;

}
