package by.epam.gym.dao;

import by.epam.gym.entities.Entity;

import java.sql.Connection;
import java.util.List;

/**
 * Abstract root class of DAO level that provide access to the database and deal with application entities.
 *
 * @author Eugene Makarenko
 * @param <K> the parameter type.
 * @param <T> the parameter type.
 *
 * @see Connection
 * @see Entity
 */
public abstract class AbstractDAO<K, T extends Entity> {
    protected Connection connection;

    public AbstractDAO(Connection connection){
        this.connection = connection;
    }

    /**
     * This method finds all entities.
     *
     * @return List of found objects.
     */
    public abstract List<T> findAll();

    /**
     * This method finds entity by id.
     *
     * @param id entity id.
     * @return entity object.
     */
    public abstract T findEntityById(K id);

    /**
     * This method deletes entity from database.
     *
     * @param entity the entity.
     * @return boolean true if entity deleted successfully, otherwise false.
     */
    public abstract boolean delete(T entity);

    /**
     * This method creates entity in database.
     *
     * @param entity the entity.
     * @return boolean true if entity created successfully, otherwise false.
     */
    public abstract boolean create(T entity);

    /**
     * This method updates entity in database.
     *
     * @param entity the entity.
     * @return boolean true if entity updated successfully, otherwise false.
     */
    public abstract boolean update(T entity);
}
