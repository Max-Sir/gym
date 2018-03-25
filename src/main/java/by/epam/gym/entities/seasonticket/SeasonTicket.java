package by.epam.gym.entities.seasonticket;

import by.epam.gym.dao.processor.ColumnName;
import by.epam.gym.entities.Entity;

import java.sql.Date;

/**
 * Class describes season ticket entity.
 *
 * @author Eugene Makarenko
 * @see SeasonTicketDurationType
 * @see Entity
 */
public class SeasonTicket extends Entity {

    private Date start;
    private Date end;
    private SeasonTicketDurationType duration;
    private int isPersonalTrainerNeed;

    /**
     * Instantiates a new SeasonTicket.
     */
    public SeasonTicket() {

    }

    /**
     * Get start date.
     *
     * @return the start date.
     */
    @ColumnName(name = "start_date", parameterIndex = 1)
    public Date getStart() {
        return start;
    }

    /**
     * Sets start date.
     *
     * @param start the start date.
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Gets the end date.
     *
     * @return the end date.
     */
    @ColumnName(name = "end_date", parameterIndex = 2)
    public Date getEnd() {
        return end;
    }

    /**
     * Sets the end date.
     *
     * @param end the end date.
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * Gets duration type of season ticket.
     *
     * @return the duration of season ticket.
     */
    @ColumnName(name = "duration", parameterIndex = 3)
    public SeasonTicketDurationType getDuration() {
        return duration;
    }

    /**
     * Sets the duration type of season ticket.
     *
     * @param duration the season ticket's duration.
     */
    public void setDuration(SeasonTicketDurationType duration) {
        this.duration = duration;
    }

    /**
     * Gets the int value of boolean variable isPersonalTrainerNeed.
     *
     * @return the isPersonalTrainerNeed.
     */
    @ColumnName(name = "is_personal_trainer_need", parameterIndex = 4)
    public int getPersonalTrainerNeed() {
        return isPersonalTrainerNeed;
    }

    /**
     * Sets the int value of boolean variable isPersonalTrainerNeed.
     *
     * @param personalTrainerNeed the int value of boolean variable isPersonalTrainerNeed.
     */
    public void setPersonalTrainerNeed(int personalTrainerNeed) {
        isPersonalTrainerNeed = personalTrainerNeed;
    }

}
