package by.epam.gym.entities.order;

import by.epam.gym.entities.Entity;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * This class describes the order of user.
 *
 * @author Eugene Makarenko
 */
public class Order extends Entity {

    private int clientId;
    private Date purchaseDate;
    private Date endDate;
    private OrderDurationType duration;
    private int isPersonalTrainerNeed;
    private BigDecimal price;
    private int isPayed;
    private String feedback;

    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    /**
     * Gets client's id.
     *
     * @return the client id.
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Sets client's id.
     *
     * @param clientId the client id.
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets order's purchase date.
     *
     * @return the purchase date.
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets order's purchase date.
     *
     * @param purchaseDate the purchase date.
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Gets order's end date.
     *
     * @return the end date.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets order's end date.
     *
     * @param endDate the end date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets order;s duration type.
     *
     * @return the duration type.
     */
    public OrderDurationType getDuration() {
        return duration;
    }

    /**
     * Sets order's duration type.
     *
     * @param duration the duration type.
     */
    public void setDuration(OrderDurationType duration) {
        this.duration = duration;
    }

    /**
     * Gets int value of variable isPersonalTrainerNeed.
     *
     * @return the int value.
     */
    public int getIsPersonalTrainerNeed() {
        return isPersonalTrainerNeed;
    }

    /**
     * Sets int value of variable isPersonalTrainerNeed.
     *
     * @param isPersonalTrainerNeed the int value.
     */
    public void setIsPersonalTrainerNeed(int isPersonalTrainerNeed) {
        this.isPersonalTrainerNeed = isPersonalTrainerNeed;
    }

    /**
     * Gets order's price.
     *
     * @return the price.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets order's price.
     *
     * @param price the price.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets int value of variable isPayed.
     *
     * @return the int value.
     */
    public int getIsPayed() {
        return isPayed;
    }

    /**
     * Sets int value of variable isPayed.
     *
     * @param isPayed the int value.
     */
    public void setIsPayed(int isPayed) {
        this.isPayed = isPayed;
    }

    /**
     * Gets order's feedback. Can be null.
     *
     * @return the feedback.
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets order's feedback. Can be null.
     *
     * @param feedback the feedback.
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
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

        Order order = (Order) object;

        if (clientId != order.clientId) {
            return false;
        }
        if (isPersonalTrainerNeed != order.isPersonalTrainerNeed) {
            return false;
        }
        if (isPayed != order.isPayed) {
            return false;
        }
        if (purchaseDate != null ? !purchaseDate.equals(order.purchaseDate) : order.purchaseDate != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(order.endDate) : order.endDate != null) {
            return false;
        }
        if (duration != order.duration) {
            return false;
        }
        if (price != null ? !price.equals(order.price) : order.price != null) {
            return false;
        }
        return feedback != null ? feedback.equals(order.feedback) : order.feedback == null;
    }

    /**
     * This method calculate object's hashcode.
     *
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + clientId;
        result = 31 * result + (purchaseDate != null ? purchaseDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + isPersonalTrainerNeed;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + isPayed;
        result = 31 * result + (feedback != null ? feedback.hashCode() : 0);
        return result;
    }

    /**
     * This method builds string information about object.
     *
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "Order{" +
                "clientId=" + clientId +
                ", purchaseDate=" + purchaseDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                ", isPersonalTrainerNeed=" + isPersonalTrainerNeed +
                ", price=" + price +
                ", isPayed=" + isPayed +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
