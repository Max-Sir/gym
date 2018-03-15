package by.epam.gym.entities.user;

import by.epam.gym.entities.Entity;

import java.util.Date;

/**
 * This class describes user of application.
 *
 * @author Eugene Makarenko
 * @see UserType
 * @see UserGender
 */
public class User extends Entity {

    private String login;
    private String password;
    private UserType userType;
    private UserGender userGender;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date birthDate;
    private int personalTrainerId;

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
     * Gets user's type.
     *
     * @return the user's type.
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Sets user's type.
     *
     * @param userType the user's type.
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * Gets user's gender.
     *
     * @return the user gender.
     */
    public UserGender getUserGender() {
        return userGender;
    }

    /**
     * Sets user's gender.
     *
     * @param userGender the user's gender.
     */
    public void setUserGender(UserGender userGender) {
        this.userGender = userGender;
    }

    /**
     * Gets user's first name.
     *
     * @return the user's first name.
     */
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

    /**
     * Gets user's email.
     *
     * @return the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets user's email.
     *
     * @param email the user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets user's phone number.
     *
     * @return the user's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets user's phone number.
     *
     * @param phoneNumber the user's phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets user's birth date.
     *
     * @return the user's birth date.
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets user's birth date.
     *
     * @param birthDate the user's birth date.
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets the user's personal trainer id.
     *
     * @return the user's personal trainer id.
     */
    public int getPersonalTrainerId() {
        return personalTrainerId;
    }

    /**
     * Sets the user's personal trainer id.
     *
     * @param personalTrainerId the user's personal trainer id.
     */
    public void setPersonalTrainerId(int personalTrainerId) {
        this.personalTrainerId = personalTrainerId;
    }
}
