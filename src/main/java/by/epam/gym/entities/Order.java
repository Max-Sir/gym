package by.epam.gym.entities;


import java.util.Date;

/**
 * This class describes the order of user.
 *
 * @author Eugene Makarenko
 */
public class Order extends Entity {

    private int clientId;
    private int seasonTicketId;
    private Date purchaseDate;

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
     * Gets season ticket's id.
     *
     * @return the season ticket's id.
     */
    public int getSeasonTicketId() {
        return seasonTicketId;
    }

    /**
     * Sets season ticket's id.
     *
     * @param seasonTicketId the season ticket's id.
     */
    public void setSeasonTicketId(int seasonTicketId) {
        this.seasonTicketId = seasonTicketId;
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
}
