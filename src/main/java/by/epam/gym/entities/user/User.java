package by.epam.gym.entities.user;

import by.epam.gym.dao.processor.ColumnName;
import by.epam.gym.entities.Entity;

import javax.validation.constraints.Null;

/**
 * This class describes user of application.
 *
 * @author Eugene Makarenko
 * @see UserRole
 * @see Entity
 */
public class User extends Entity {

    private String login;
    private String password;
    private UserRole userRole;
    private String firstName;
    private String lastName;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Gets user's login.
     *
     * @return the user's login.
     */
    @ColumnName(name = "login", parameterIndex = 1)
    public String getLogin() {
        return login;
    }

    /**
     * Sets user's login.
     *
     * @param login the user's login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets user's password.
     *
     * @return the user's password.
     */
    @ColumnName(name = "password", parameterIndex = 2)
    public String getPassword() {
        return password;
    }

    /**
     * Sets user's password.
     *
     * @param password the user's password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets user's role.
     *
     * @return the user's role.
     */
    @ColumnName(name = "role", parameterIndex = 3)
    public UserRole getUserRole() {
        return userRole;
    }

    /**
     * Sets user's role.
     *
     * @param userRole the user's role.
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }


    /**
     * Gets user's first name.
     *
     * @return the user's first name.
     */
    @ColumnName(name = "first_name", parameterIndex = 4)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets user's first name.
     *
     * @param firstName the user's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets user's last name.
     *
     * @return the user's last name.
     */
    @ColumnName(name = "last_name", parameterIndex = 5)
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets user's last name.
     *
     * @param lastName the user's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
