package by.epam.gym.dao;

import by.epam.gym.entities.Entity;
import by.epam.gym.exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * Abstract root class of DAO level that provide access to the database and deal with application entities.
 *
 * @param <K> the parameter type.
 * @param <T> the parameter type.
 * @author Eugene Makarenko
 * @see Connection
 * @see Entity
 * @see DAOException
 */
public abstract class AbstractDAO<K, T extends Entity> {
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
    public abstract List<T> findAll() throws DAOException;

    /**
     * This method finds entity from database by id.
     *
     * @param id entity id.
     * @return true if entity deleted successfully, otherwise false.
     * @throws DAOException object if execution of query is failed.
     */
    public abstract T findEntityById(K id) throws DAOException;

    /**
     * This method deletes entity from database by id.
     *
     * @param id entity id.
     * @return true if entity deleted successfully, otherwise false.
     * @throws DAOException object if execution of query is failed.
     */
    public abstract boolean delete(K id) throws DAOException;

    /**
     * This method deletes entity from database.
     *
     * @param entity the entity.
     * @return boolean true if entity deleted successfully, otherwise false.
     * @throws DAOException object if execution of query is failed.
     */
    public abstract boolean delete(T entity) throws DAOException;

    /**
     * This method creates entity in database.
     *
     * @param entity the entity.
     * @return boolean true if entity created successfully, otherwise false.
     * @throws DAOException object if execution of query is failed.
     */
    public abstract boolean create(T entity) throws DAOException;

    /**
     * This method updates entity in database.
     *
     * @param entity the entity.
     * @return boolean true if entity updated successfully, otherwise false.
     * @throws DAOException object if execution of query is failed.
     */
    public abstract boolean update(T entity) throws DAOException;

    /**
     * Factory method creates entity.
     *
     * @param resultSet the result set of statement.
     * @return the entity.
     * @throws DAOException object if execution of query is failed.
     */
    public abstract T createEntity(ResultSet resultSet) throws DAOException;
}
