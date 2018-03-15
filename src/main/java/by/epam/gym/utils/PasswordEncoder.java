package by.epam.gym.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Util class for encoding user password.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.user.User
 * @see DigestUtils
 */
public class PasswordEncoder {

    private static final String PASSWORD_PREFIX = "MD5_";

    /**
     * Encode password using sha256 algorithm.
     *
     * @param password the user's password.
     * @return the encoded user's password.
     */
    public static String encode(String password) {
        password = PASSWORD_PREFIX + password;
        password = DigestUtils.sha256Hex(password);

        return password;
    }

}
