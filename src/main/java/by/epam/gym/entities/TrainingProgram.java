package by.epam.gym.entities;

import java.sql.Date;

/**
 * Class describes training program of user.
 *
 * @author Eugene Makarenko
 * @see Entity
 */
public class TrainingProgram extends Entity {

    private int authorId;
    private Integer personalTrainerId;
    private int clientId;
    private Date startDate;
    private Date endDate;
    private String diet;

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
     * Gets training program's personal trainer id. Can be null.
     *
     * @return the training program's persona trainer id.
     */
    public Integer getPersonalTrainerId() {
        return personalTrainerId;
    }

    /**
     * Sets training program's personal trainer id. Can be null.
     *
     * @param personalTrainerId the training program's personal trainer id.
     */
    public void setPersonalTrainerId(Integer personalTrainerId) {
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
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets training program's start date.
     *
     * @param startDate the training program's start date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets training program's end date.
     *
     * @return the training program's end date.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets training program's end date.
     *
     * @param endDate the training program's end date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets training program's diet.
     *
     * @return the diet.
     */
    public String getDiet() {
        return diet;
    }

    /**
     * Sets training program's diet.
     *
     * @param diet the diet.
     */
    public void setDiet(String diet) {
        this.diet = diet;
    }

    /**
     * This method equals two objects.
     *
     * @param object the object.
     * @return true if objects are equal and false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }

        TrainingProgram that = (TrainingProgram) object;

        if (authorId != that.authorId) {
            return false;
        }
        if (clientId != that.clientId) {
            return false;
        }
        if (personalTrainerId != null ? !personalTrainerId.equals(that.personalTrainerId) : that.personalTrainerId != null) {
            return false;
        }
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) {
            return false;
        }
        return diet != null ? diet.equals(that.diet) : that.diet == null;
    }

    /**
     * This method calculate object's hashcode.
     *
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + authorId;
        result = 31 * result + (personalTrainerId != null ? personalTrainerId.hashCode() : 0);
        result = 31 * result + clientId;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (diet != null ? diet.hashCode() : 0);
        return result;
    }

    /**
     * This method builds string information about object.
     *
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "TrainingProgram{" +
                "authorId=" + authorId +
                ", personalTrainerId=" + personalTrainerId +
                ", clientId=" + clientId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", diet='" + diet + '\'' +
                '}';
    }
}
