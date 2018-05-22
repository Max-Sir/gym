package by.epam.gym.pool;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread safe connection pool.
 *
 * @author Eugene Makarenko
 * @see LinkedList
 * @see Lock
 * @see ConnectionCreator
 */
public class ConnectionPool {

    private final static Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private static Lock instanceLocker = new ReentrantLock();
    private static Lock poolLocker = new ReentrantLock();
    private static Condition poolCondition = poolLocker.newCondition();

    private static ConnectionPool instance = null;
    private static AtomicBoolean isInstanceAvailable = new AtomicBoolean(true);

    private final LinkedList<Connection> pool;

    private ConnectionPool() {
        ConnectionCreator connectionCreator = new ConnectionCreator();
        pool = connectionCreator.createPool();
    }

    /**
     * Get instance of connection pool class.
     *
     * @return instance.
     */
    public static ConnectionPool getInstance() {

        if (isInstanceAvailable.get()) {
            instanceLocker.lock();
            try {
                boolean isInstanceAvailableNow = instance == null;
                if (isInstanceAvailableNow) {
                    instance = new ConnectionPool();
                    isInstanceAvailable.set(false);
                }
            } finally {
                instanceLocker.unlock();
            }
        }

        return instance;
    }

    /**
     * Get and remove connection from pool.
     *
     * @return first connection from pool.
     */
    public Connection getConnection() {
        Connection connection;
        poolLocker.lock();

        try {

            if (pool.isEmpty()) {
                poolCondition.await();
            }

            connection = pool.poll();
        } catch (InterruptedException exception) {
            throw new IllegalStateException("Can't get connection. ", exception);
        } finally {
            poolLocker.unlock();
        }

        return connection;
    }

    /**
     * Adds chosen connection back to pool.
     *
     * @param connection to database, that was get from pool.
     */
    public void returnConnection(Connection connection) {
        poolLocker.lock();

        try {
            pool.addLast(connection);
            poolCondition.signal();
        } finally {
            poolLocker.unlock();
        }
    }

    /**
     * Close all connections in pool.
     */
    public void closePool() {
        for (Connection connection : pool) {
            try {
                connection.close();
            } catch (SQLException exception) {
                LOGGER.error("Exception was detected during pool closing.", exception);
            }
        }
    }
}
