package by.epam.gym.dao;

import by.epam.gym.entities.Entity;
import by.epam.gym.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract root class of DAO level that provide access to the database and deal with application entities.
 *
 * @param <T> the entity type.
 * @author Eugene Makarenko
 * @see Connection
 * @see Entity
 * @see DAOException
 */
public abstract class AbstractDAO<T extends Entity> {

    private static final String SELECT_FROM_SQL_QUERY = "SELECT * FROM ";
    private static final String DELETE_SQL_QUERY = "DELETE FROM ";
    private static final String WHERE_SQL_QUERY = " WHERE id=?";

    protected Connection connection;

    public AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * This method finds all entities.
     *
     * @return List of found objects.
     * @throws DAOException object if execution of query is failed.
     */
    public List<T> findAll() throws DAOException{
        String sqlQuery = SELECT_FROM_SQL_QUERY + getTableName();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){

            List<T> entities = new ArrayList<T>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                T entity = createEntity(resultSet);
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
    public T findEntityById(int id) throws DAOException{
        String sqlQuery = SELECT_FROM_SQL_QUERY + getTableName() + WHERE_SQL_QUERY;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1,id);

            T entity = null;

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                entity = createEntity(resultSet);
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
     * @return true if entity deleted successfully, else false.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean deleteById(int id) throws DAOException{
        String sqlQuery = DELETE_SQL_QUERY + getTableName() + WHERE_SQL_QUERY;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1,id);

            int queryResult = preparedStatement.executeUpdate();

            return queryResult > 0;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * This method creates entity in database.
     *
     * @param entity the entity.
     * @return boolean true if entity created successfully, otherwise false.
     * @throws DAOException object if execution of query is failed.
     */
    public abstract boolean insert(T entity) throws DAOException;

    /**
     * Factory method creates entity.
     *
     * @param resultSet the result set of statement.
     * @return the entity.
     * @throws DAOException object if execution of query is failed.
     */
    public abstract T createEntity(ResultSet resultSet) throws DAOException;

    /**
     * Gets table name for current DAO.
     *
     * @return the table name.
     */
    public abstract String getTableName();
}
