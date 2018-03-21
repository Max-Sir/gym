package by.epam.gym.pool;

import by.epam.gym.exceptions.ConnectionException;
import org.apache.log4j.Logger;

import java.rmi.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;
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

    public static final String RESOURCE_BUNDLE_FILE_NAME = "database";

    private static final String POOL_SIZE_PROPERTY_VALUE = "db.poolSize";

    private final static Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private static ConnectionCreator connectionCreator = new ConnectionCreator();
    private static Lock locker = new ReentrantLock();
    private static Condition poolCondition = locker.newCondition();

    private static ConnectionPool instance = null;
    private static AtomicBoolean isInstanceAvailable = new AtomicBoolean(true);

    private final LinkedList<Connection> pool;

    private ConnectionPool() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_FILE_NAME);
        pool = new LinkedList<>();
        String poolSizeValue = resourceBundle.getString(POOL_SIZE_PROPERTY_VALUE);
        int currentPoolSize = Integer.parseInt(poolSizeValue);

        for (int listIndex = 0; listIndex < currentPoolSize; listIndex++) {
            Connection currentConnection = connectionCreator.create();

            pool.addLast(currentConnection);
        }

    }

    /**
     * Get instance of connection pool class.
     *
     * @return instance.
     */
    public static ConnectionPool getInstance() {

        if (isInstanceAvailable.get()) {
            locker.lock();
            try {
                boolean isInstanceAvailableNow = instance == null;
                if (isInstanceAvailableNow) {
                    instance = new ConnectionPool();
                    isInstanceAvailable.set(false);
                }
            } finally {
                locker.unlock();
            }
        }

        return instance;
    }

    /**
     * Get and remove connection from pool.
     *
     * @return first connection from pool.
     */
    public Connection getConnection() throws ConnectionException {
        Connection connection;
        locker.lock();

        try {

            if (pool.isEmpty()){
                poolCondition.await();
            }

            connection = pool.poll();
        } catch (InterruptedException exception) {
            LOGGER.warn("Interrupted exception detected. ", exception);
            throw new ConnectionException("Can't get connection. ", exception);
        } finally {
            locker.unlock();
        }

        return connection;
    }

    /**
     * Adds chosen connection back to pool.
     *
     * @param connection to database, that was get from pool.
     */
    public void returnConnection(Connection connection) {
        locker.lock();

        try {
            pool.addLast(connection);

            poolCondition.signal();
        } finally {
            locker.unlock();
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
                LOGGER.error(exception);
            }
        }
    }
}
