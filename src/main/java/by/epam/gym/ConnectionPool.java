package by.epam.gym;

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
 * @author Eugene Makarenko
 */
public class ConnectionPool {

    private static final String RESOURCE_BUNDLE_FILE_NAME = "database";

    private static final String POOL_SIZE_PROPERTY_VALUE = "db.poolSize";
    private static final String USER_PROPERTY_VALUE = "db.user";
    private static final String PASSWORD_PROPERTY_VALUE = "db.password";
    private static final String AUTO_RECONNECT_PROPERTY_VALUE = "db.autoReconnect";
    private static final String CHARACTER_ENCODING_PROPERTY_VALUE = "db.encoding";
    private static final String UNICODE_PROPERTY_VALUE = "db.useUnicode";
    private static final String URL_PROPERTY_VALUE = "db.url";

    private static final String USER_PROPERTY = "user";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String AUTO_RECONNECT_PROPERTY = "autoReconnect";
    private static final String CHARACTER_ENCODING_PROPERTY = "characterEncoding";
    private static final String UNICODE_PROPERTY = "useUnicode";

    private final static Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(RESOURCE_BUNDLE_FILE_NAME);
    private final static Lock LOCK = new ReentrantLock();

    private static ConnectionPool instance = null;
    private static AtomicBoolean isInstanceAvailable = new AtomicBoolean(true);

    private final LinkedList<Connection> pool;

    private ConnectionPool() {
        String poolSizeValue = RESOURCE_BUNDLE.getString(POOL_SIZE_PROPERTY_VALUE);
        int currentPoolSize = Integer.parseInt(poolSizeValue);
        pool = new LinkedList<Connection>();

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException exception) {
            throw new ExceptionInInitializerError("Driver hasn't been registered. " + exception.getMessage());
        }

        String connectionUrlValue = RESOURCE_BUNDLE.getString(URL_PROPERTY_VALUE);
        String userValue = RESOURCE_BUNDLE.getString(USER_PROPERTY_VALUE);
        String passwordValue = RESOURCE_BUNDLE.getString(PASSWORD_PROPERTY_VALUE);
        String autoReconnectValue = RESOURCE_BUNDLE.getString(AUTO_RECONNECT_PROPERTY_VALUE);
        String characterEncodingValue = RESOURCE_BUNDLE.getString(CHARACTER_ENCODING_PROPERTY_VALUE);
        String unicodeValue = RESOURCE_BUNDLE.getString(UNICODE_PROPERTY_VALUE);

        Properties properties = new Properties();
        properties.put(USER_PROPERTY, userValue);
        properties.put(PASSWORD_PROPERTY, passwordValue);
        properties.put(AUTO_RECONNECT_PROPERTY,autoReconnectValue);
        properties.put(CHARACTER_ENCODING_PROPERTY, characterEncodingValue);
        properties.put(UNICODE_PROPERTY, unicodeValue);

        for (int listIndex = 0; listIndex < currentPoolSize; listIndex++) {
            try{
                Connection connection = DriverManager.getConnection(connectionUrlValue, properties);

                pool.add(connection);
            } catch (SQLException exception) {
                throw new ExceptionInInitializerError("Connection hasn't been created. " + exception.getMessage());
            }
        }
    }

    /**
     * Get instance of connection pool.
     * @return instance of connection pool.
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
