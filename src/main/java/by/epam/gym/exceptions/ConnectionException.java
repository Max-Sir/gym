package by.epam.gym.exceptions;

/**
 *
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.pool.ConnectionPool
 */
public class ConnectionException extends Exception {

    /**
     * Instantiates a new ConnectionException.
     */
    public ConnectionException() {
    }

    /**
     * Instantiates a new ConnectionException.
     *
     * @param message the message.
     */
    public ConnectionException(String message) {
        super(message);
    }

    /**
     * Instantiates a new ConnectionException.
     *
     * @param message the message.
     * @param cause   the cause.
     */
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new ConnectionException.
     *
     * @param cause the cause.
     */
    public ConnectionException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new ConnectionException.
     *
     * @param message            the message.
     * @param cause              the cause.
     * @param enableSuppression  the enable suppression.
     * @param writableStackTrace the writable stack trace.
     */
    public ConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
