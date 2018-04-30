package by.epam.gym.pool;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Util class that creates connection to database using file resources.
 *
 * @author Eugene Makarenko
 * @see Connection
 * @see Properties
 * @see ResourceBundle
 */
public class ConnectionCreator {

    private static final Logger LOGGER = Logger.getLogger(ConnectionCreator.class);

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

    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(RESOURCE_BUNDLE_FILE_NAME);

    /**
     * Create pool of connections to chosen database.
     *
     * @return LinkedList object.
     */
    public LinkedList<Connection> createPool() {
        LinkedList<Connection> pool = new LinkedList<>();
        String poolSizeValue = RESOURCE_BUNDLE.getString(POOL_SIZE_PROPERTY_VALUE);
        int currentPoolSize = Integer.parseInt(poolSizeValue);

        for (int listIndex = 0; listIndex < currentPoolSize; listIndex++) {
            Connection connection = create();

            pool.addLast(connection);
        }

        LOGGER.info("Pool was created successful.");
        return pool;
    }

    /**
     * Create connection to chosen database using properties.
     *
     * @return created connection.
     */
    private Connection create() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            LOGGER.info("Driver was registered successful.");
        } catch (SQLException exception) {
            LOGGER.warn("SQL exception was detected during driver registration.");
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
        properties.put(AUTO_RECONNECT_PROPERTY, autoReconnectValue);
        properties.put(CHARACTER_ENCODING_PROPERTY, characterEncodingValue);
        properties.put(UNICODE_PROPERTY, unicodeValue);

        try {
            Connection connection = DriverManager.getConnection(connectionUrlValue, properties);

            LOGGER.info("Connection was created successful.");
            return connection;
        } catch (SQLException exception) {
            LOGGER.warn("SQL exception was detected during connection creating.");
            throw new ExceptionInInitializerError("Connection hasn't been created. " + exception.getMessage());
        }
    }

}
