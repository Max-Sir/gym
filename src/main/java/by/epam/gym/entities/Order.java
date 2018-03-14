package by.epam.gym.entities;


import java.util.Date;

/**
 * This class describes the order of user.
 *
 * @author Eugene Makarenko
 */
public class Order extends Entity {

    private int clientId;
    private int orderExecutorId;
    private int subscriptionId;
    private Date purchaseDate;
    private String appointment;

    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    /**
     * Gets order's client id.
     *
     * @return the order's client id.
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Sets order's client id.
     *
     * @param clientId the order's client id.
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets order's executor id.
     *
     * @return the order's executor id.
     */
    public int getOrderExecutorId() {
        return orderExecutorId;
    }

    /**
     * Sets order's executor id.
     *
     * @param orderExecutorId the order's executor id.
     */
    public void setOrderExecutorId(int orderExecutorId) {
        this.orderExecutorId = orderExecutorId;
    }

    /**
     * Gets order's subscription id.
     *
     * @return the order's subscription id.
     */
    public int getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * Sets order's subscription id.
     *
     * @param subscriptionId the order's subscription id.
     */
    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    /**
     * Gets order's purchase date.
     *
     * @return the order's purchase date.
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets order's purchase date.
     *
     * @param purchaseDate the order's purchase date.
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Gets order's appointment, made by order's executor.
     *
     * @return the order's appointment.
     */
    public String getAppointment() {
        return appointment;
    }

    /**
     * Sets order's appointment, made by order's executor.
     *
     * @param appointment the order's appointment.
     */
    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }
}
