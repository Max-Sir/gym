package by.epam.gym.entities;

import by.epam.gym.dao.processor.ColumnName;

import java.sql.Date;

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
    @ColumnName(name = "author_id", parameterIndex = 1)
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
    @ColumnName(name = "personal_trainer_id", parameterIndex = 2)
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
    @ColumnName(name = "client_id", parameterIndex = 3)
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
    @ColumnName(name = "start_date", parameterIndex = 4)
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
    @ColumnName(name = "end_date", parameterIndex = 5)
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
