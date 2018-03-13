package by.epam.gym.pool;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread safe connection pool.
 *
 * @author Eugene Makarenko
 */
public class ConnectionPool {

    private final static Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private final static ConnectionCreator CONNECTION_CREATOR = new ConnectionCreator();
    private final static Lock LOCK = new ReentrantLock();

    private static ConnectionPool instance = null;
    private static AtomicBoolean isInstanceAvailable = new AtomicBoolean(true);

    private final LinkedList<Connection> pool;

    private ConnectionPool() {

        pool = CONNECTION_CREATOR.createConnections();

    }

    /**
     * Get instance of connection pool class.
     * @return instance.
     */
    public static ConnectionPool getInstance() {

        if (isInstanceAvailable.get()) {
            LOCK.lock();
            try {

                boolean isInstanceAvailableNow = instance == null;
                if (isInstanceAvailableNow) {
                    instance = new ConnectionPool();
                    isInstanceAvailable.set(false);
                }
            } finally {
                LOCK.unlock();
            }
        }

        return instance;
    }

    /**
     * Get and remove connection from pool.
     * @return first connection from pool.
     */
    public Connection getConnection(){
        Connection connection;
        LOCK.lock();

        try {
            connection = pool.poll();
        } finally {
            LOCK.unlock();
        }

        return connection;
    }

    /**
     * Adds chosen connection back to pool.
     * @param connection to database, that was get from pool.
     */
    public void returnConnection(Connection connection){
        LOCK.lock();

        try{
            pool.addLast(connection);
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * Close all connections in pool.
     */
    public void closePool(){
        for (Connection connection : pool) {
            try {
                connection.close();
            } catch (SQLException exception) {
                LOGGER.error(exception);
            }
        }
    }
}
