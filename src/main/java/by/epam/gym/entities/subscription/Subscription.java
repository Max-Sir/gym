package by.epam.gym.entities.subscription;

import by.epam.gym.entities.Entity;

import java.math.BigDecimal;

/**
 * This class describes the subscription of user.
 *
 * @author Eugene Makarenko
 * @see TrainingsCountType
 * @see DurationType
 */
public class Subscription extends Entity {

    private TrainingsCountType trainingsCountType;
    private DurationType durationType;
    private int trainings_count;
    private boolean isPersonalTrainerNeed;
    private BigDecimal price;

    /**
     * Instantiates a new Subscription.
     */
    public Subscription() {
    }

    /**
     * Gets subscription's trainings count type.
     *
     * @return the subscription's trainings count type.
     */
    public TrainingsCountType getTrainingsCountType() {
        return trainingsCountType;
    }

    /**
     * Sets subscription's trainings count type.
     *
     * @param trainingsCountType the subscription's trainings count type.
     */
    public void setTrainingsCountType(TrainingsCountType trainingsCountType) {
        this.trainingsCountType = trainingsCountType;
    }

    /**
     * Gets subscription's duration type.
     *
     * @return the subscription's duration type.
     */
    public DurationType getDurationType() {
        return durationType;
    }

    /**
     * Sets subscription's duration type.
     *
     * @param durationType the subscription's duration type.
     */
    public void setDurationType(DurationType durationType) {
        this.durationType = durationType;
    }

    /**
     * Gets subscription's trainings count.
     *
     * @return the subscription's trainings count.
     */
    public int getTrainings_count() {
        return trainings_count;
    }

    /**
     * Sets subscription's trainings count.
     *
     * @param trainings_count the subscription's trainings count.
     */
    public void setTrainings_count(int trainings_count) {
        this.trainings_count = trainings_count;
    }

    /**
     * Gets subscription's boolean variable isPersonalTrainerNeed.
     *
     * @return the subscription's boolean variable isPersonalTrainerNeed.
     */
    public boolean isPersonalTrainerNeed() {
        return isPersonalTrainerNeed;
    }

    /**
     * Sets subscription's boolean variable isPersonalTrainerNeed.
     *
     * @param personalTrainerNeed the subscription's boolean variable isPersonaTrainerNeed.
     */
    public void setPersonalTrainerNeed(boolean personalTrainerNeed) {
        isPersonalTrainerNeed = personalTrainerNeed;
    }

    /**
     * Gets subscription's price.
     *
     * @return the subscription's price.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets subscription's price.
     *
     * @param price the subscription's price.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
