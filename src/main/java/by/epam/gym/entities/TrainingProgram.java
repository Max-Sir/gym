package by.epam.gym.entities;

import java.util.Date;

/**
 * Class describes training program of user.
 *
 * @author Eugene Makarenko
 * @see Entity
 */
public class TrainingProgram extends Entity {

    private int authorId;
    private int personalTrainerId;
    private int clientId;
    private Date start;
    private Date end;

    /**
     * Instantiates a new TrainingProgram.
     */
    public TrainingProgram() {
    }

    /**
     * Gets training program's author id.
     *
     * @return the training program's author id.
     */
    public int getAuthorId() {
        return authorId;
    }

    /**
     * Sets training program's author id.
     *
     * @param authorId the training program's author id.
     */
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * Gets training program's personal trainer id.
     *
     * @return the training program's persona trainer id.
     */
    public int getPersonalTrainerId() {
        return personalTrainerId;
    }

    /**
     * Sets training program's personal trainer id.
     *
     * @param personalTrainerId the training program's personal trainer id.
     */
    public void setPersonalTrainerId(int personalTrainerId) {
        this.personalTrainerId = personalTrainerId;
    }

    /**
     * Gets training program's client id.
     *
     * @return the training program's client id.
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Sets training program's client id.
     *
     * @param clientId the training program's client id.
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets training program's start date.
     *
     * @return the training program's start date.
     */
    public Date getStart() {
        return start;
    }

    /**
     * Sets training program's start date.
     *
     * @param start the training program's start date.
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Gets training program's end date.
     *
     * @return the training program's end date.
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Sets training program's end date.
     *
     * @param end the training program's end date.
     */
    public void setEnd(Date end) {
        this.end = end;
    }
}
