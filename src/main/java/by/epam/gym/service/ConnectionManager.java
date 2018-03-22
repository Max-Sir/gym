package by.epam.gym.service;

import by.epam.gym.dao.AbstractDAOImpl;
import by.epam.gym.exceptions.ConnectionException;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.pool.ConnectionPool;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class of service level to work with connection from pool.
 *
 * @author Eugene Makarenko
 * @see Connection
 * @see ConnectionPool
 * @see AbstractDAOImpl
 * @param <T> type of DAO class.
 */
public class ConnectionManager<T extends AbstractDAOImpl> implements AutoCloseable {

    private Class<T> classDAO;
    private ConnectionPool connectionPool;
    private final Connection connection;

    /**
     * Instantiates a new ConnectionManager.
     *
     */
    public ConnectionManager(Class<T> classDAO) throws ConnectionException {
        this.classDAO = classDAO;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
    }

    /**
     * Creates DAO instance using reflection.
     *
     * @return type instance.
     * @throws ServiceException object if execution of method is failed.
     */
    public T createDAO() throws ServiceException {
        try {
           Constructor<T> constructor = classDAO.getConstructor(Connection.class);

           T type = constructor.newInstance(connection);

            return type;
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
           throw new ServiceException("Reflection exception detected. ", exception);
        }
    }

    /**
     * The method starts transaction.
     *
     * @throws ServiceException object if execution of method is failed.
     */
    public void startTransaction() throws ServiceException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            throw new ServiceException("SQL exception detected. ", exception);
        }
    }

    /**
     * The method commits transaction.
     *
     * @throws ServiceException object if execution of method is failed.
     */
    public void commitTransaction() throws ServiceException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            throw new ServiceException("SQL exception detected. ", exception);
        }
    }

    /**
     * The method rollback transaction.
     *
     * @throws ServiceException object if execution of method is failed.
     */
    public void rollbackTransaction() throws ServiceException {
        try {
            connection.rollback();
        } catch (SQLException exception) {
            throw new ServiceException("SQL exception detected. ", exception);
        }
    }

    /**
     * Implementation of AutoCloseable interface to work with try().
     *
     * @throws Exception object if execution of method is failed.
     */
    @Override
    public void close() throws Exception {
        connectionPool.returnConnection(connection);
    }
}
