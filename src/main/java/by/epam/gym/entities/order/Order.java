package by.epam.gym.entities.order;


import by.epam.gym.dao.processor.ColumnName;
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
    private Integer isPersonalTrainerNeed;
    private BigDecimal price;
    private int isPayed;
    private String feedback;
    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    @ColumnName(name = "clientId", parameterIndex = 1)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @ColumnName(name = "purchase_date", parameterIndex = 2)
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @ColumnName(name = "end_date", parameterIndex = 3)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @ColumnName(name = "duration", parameterIndex = 4)
    public OrderDurationType getDuration() {
        return duration;
    }

    public void setDuration(OrderDurationType duration) {
        this.duration = duration;
    }

    @ColumnName(name = "is_personal_trainer_need", parameterIndex = 5)
    public Integer getIsPersonalTrainerNeed() {
        return isPersonalTrainerNeed;
    }

    public void setIsPersonalTrainerNeed(Integer isPersonalTrainerNeed) {
        this.isPersonalTrainerNeed = isPersonalTrainerNeed;
    }

    @ColumnName(name = "price",parameterIndex = 6)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ColumnName(name = "is_payed",parameterIndex = 7)
    public int getIsPayed() {
        return isPayed;
    }

    public void setIsPayed(int isPayed) {
        this.isPayed = isPayed;
    }

    @ColumnName(name = "feedback", parameterIndex = 8)
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
