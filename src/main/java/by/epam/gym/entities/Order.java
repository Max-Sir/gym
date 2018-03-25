package by.epam.gym.entities;


import by.epam.gym.dao.processor.ColumnName;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * This class describes the order of user.
 *
 * @author Eugene Makarenko
 */
public class Order extends Entity {

    private int clientId;
    private int seasonTicketId;
    private Date purchaseDate;
    private BigDecimal price;
    private String feedback;
    private int isPayed;

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
    @ColumnName(name = "client_id", parameterIndex = 1)
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
    @ColumnName(name = "season_ticket_id",parameterIndex = 2)
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
    @ColumnName(name = "purchase_date", parameterIndex = 3)
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
     * Gets order's price.
     *
     * @return the order's price.
     */
    @ColumnName(name = "price", parameterIndex = 4)
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets order's price.
     *
     * @param price the order's price.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets order's feedback.
     *
     * @return the feedback.
     */
    @ColumnName(name = "feedback", parameterIndex = 5)
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets order's feedback.
     *
     * @param feedback the order's feedback.
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the int value of boolean variable isPayed.
     *
     * @return the isPayed value.
     */
    @ColumnName(name = "is_payed", parameterIndex = 6)
    public int getIsPayed() {
        return isPayed;
    }

    /**
     * Sets the int value of boolean variable isPayed.
     *
     * @param isPayed the int value of boolean variable isPayed.
     */
    public void setIsPayed(int isPayed) {
        this.isPayed = isPayed;
    }

}
