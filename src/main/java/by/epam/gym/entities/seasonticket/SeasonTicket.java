package by.epam.gym.entities.seasonticket;

import by.epam.gym.entities.Entity;

import java.math.BigDecimal;
import java.util.Date;

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
    private boolean isPersonalTrainerNeed;
    private BigDecimal price;

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
     * Gets the boolean value of variable isPersonalTrainerNeed.
     *
     * @return the isPersonalTrainerNeed.
     */
    public boolean getPersonalTrainerNeed() {
        return isPersonalTrainerNeed;
    }

    /**
     * Sets the boolean value of variable isPersonalTrainerNeed.
     *
     * @param personalTrainerNeed the boolean variable isPersonalTrainerNeed.
     */
    public void setPersonalTrainerNeed(boolean personalTrainerNeed) {
        isPersonalTrainerNeed = personalTrainerNeed;
    }

    /**
     * Gets season ticket's price.
     *
     * @return the season ticket's price.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets season ticket's price.
     *
     * @param price the season ticket's price.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
