package by.epam.gym.dao;

import by.epam.gym.entities.order.Order;
import by.epam.gym.entities.order.OrderDurationType;
import by.epam.gym.exceptions.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that provide access to the database and deal with Order entity.
 *
 * @author Eugene Makarenko
 * @see AbstractDAOImpl
 * @see Order
 */
public class OrderDAOImpl extends AbstractDAOImpl<Order> {

    /**
     * Common queries.
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM orders";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM orders WHERE id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM orders WHERE id=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO orders (client_id, purchase_date, end_date, duration, is_personal_trainer_need, price, is_payed, feedback)VALUES(?,?,?,?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE orders SET client_id=?, purchase_date=?, end_date=?, duration=?, is_personal_trainer_need=?, price=?, is_payed=?, feedback=? WHERE id=?";

    private static final String SELECT_CLIENT_ORDERS_QUERY = "SELECT * FROM orders WHERE client_id=?";
    private static final String SELECT_CLIENT_ACTUAL_ORDER_QUERY = "SELECT * FROM orders WHERE client_id=? AND end_date>=CURDATE()";
    private static final String SELECT_PRICE_FOR_ORDER_QUERY = "SELECT price FROM prices WHERE order_type=?";
    private static final String UPDATE_FEEDBACK_QUERY = "UPDATE orders SET feedback=? WHERE id=?";

    private static final String CLIENT_ID_COLUMN_LABEL = "client_id";
    private static final String PURCHASE_DATE_COLUMN_LABEL = "purchase_date";
    private static final String END_DATE_COLUMN_LABEL = "end_date";
    private static final String DURATION_COLUMN_LABEL = "duration";
    private static final String IS_PERSONAL_TRAINER_NEED = "is_personal_trainer_need";
    private static final String PRICE_COLUMN_LABEL = "price";
    private static final String IS_PAYED_COLUMN_LABEL = "is_payed";
    private static final String FEEDBACK_COLUMN_LABEL = "feedback";

    private static final int PERSONAL_TRAINER_NEED_TRUE_INDEX = 1;

    private static final String PRICE_WITH_TRAINER_PARAMETER_PART = "_WITH_TRAINER";

    /**
     * Instantiates a new OrderDAOImpl.
     *
     * @param connection the connection to database.
     */
    public OrderDAOImpl(Connection connection) {
        super(connection);
    }

    /**
     * This method update feedback in order.
     *
     * @param orderId  the order's id.
     * @param feedback the feedback
     * @return true if operation was successful and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean updateFeedback(String feedback, int orderId) throws DAOException {

        return executeQuery(UPDATE_FEEDBACK_QUERY, feedback, orderId);

    }

    /**
     * This method select all client's orders from database.
     *
     * @param clientId the client id.
     * @return List of orders.
     * @throws DAOException object if execution of query is failed.
     */
    public List<Order> selectClientOrders(int clientId) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_CLIENT_ORDERS_QUERY, clientId)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();

            while (resultSet.next()) {
                Order order = buildEntity(resultSet);

                orders.add(order);
            }
            return orders;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method select price from database.
     *
     * @param duration              the order's duration
     * @param isPersonalTrainerNeed the int value of variable isPersonalTrainerNeed.
     * @return the price.
     * @throws DAOException object if execution of query is failed.
     */
    public BigDecimal selectPriceForOrder(OrderDurationType duration, int isPersonalTrainerNeed) throws DAOException {
        String parameter = String.valueOf(duration);
        if (isPersonalTrainerNeed == PERSONAL_TRAINER_NEED_TRUE_INDEX) {
            parameter += PRICE_WITH_TRAINER_PARAMETER_PART;
        }
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_PRICE_FOR_ORDER_QUERY, parameter)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            BigDecimal price = null;
            if (resultSet.next()) {
                price = resultSet.getBigDecimal(PRICE_COLUMN_LABEL);
            }

            return price;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method checks client for having actual order.
     *
     * @param clientId the client's id.
     * @return true if client has actual order and false otherwise.
     * @throws DAOException object if execution of query is failed.
     */
    public boolean hasClientActualOrder(int clientId) throws DAOException {
        try (PreparedStatement preparedStatement = prepareStatementForQuery(SELECT_CLIENT_ACTUAL_ORDER_QUERY, clientId)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method gets entity's parameters.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    @Override
    protected List<String> getEntityParameters(Order entity) {
        List<String> parameters = new ArrayList<>();

        int clientId = entity.getClientId();
        String clientIdValue = String.valueOf(clientId);
        parameters.add(clientIdValue);

        Date purchaseDate = entity.getPurchaseDate();
        String purchaseDateValue = String.valueOf(purchaseDate);
        parameters.add(purchaseDateValue);

        Date endDate = entity.getEndDate();
        String endDateValue = String.valueOf(endDate);
        parameters.add(endDateValue);

        OrderDurationType duration = entity.getDuration();
        String durationValue = String.valueOf(duration);
        parameters.add(durationValue);

        int isPersonalTrainerNeed = entity.getIsPersonalTrainerNeed();
        String isPersonalTrainerNeedValue = String.valueOf(isPersonalTrainerNeed);
        parameters.add(isPersonalTrainerNeedValue);

        BigDecimal price = entity.getPrice();
        String priceValue = String.valueOf(price);
        parameters.add(priceValue);

        int isPayed = entity.getIsPayed();
        String isPayedValue = String.valueOf(isPayed);
        parameters.add(isPayedValue);

        String feedback = entity.getFeedback();
        if (feedback == null) {
            parameters.add(NULL_PARAMETER);
        } else {
            parameters.add(feedback);
        }

        return parameters;
    }

    /**
     * This method builds Order object from ResultSet object.
     *
     * @param resultSet the result set of statement.
     * @return the Order object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    protected Order buildEntity(ResultSet resultSet) throws DAOException {
        try {
            Order order = new Order();

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            order.setId(id);

            int clientId = resultSet.getInt(CLIENT_ID_COLUMN_LABEL);
            order.setClientId(clientId);

            Date purchaseDate = resultSet.getDate(PURCHASE_DATE_COLUMN_LABEL);
            order.setPurchaseDate(purchaseDate);

            Date endDate = resultSet.getDate(END_DATE_COLUMN_LABEL);
            order.setEndDate(endDate);

            String durationValue = resultSet.getString(DURATION_COLUMN_LABEL);
            OrderDurationType duration = OrderDurationType.valueOf(durationValue);
            order.setDuration(duration);

            int isPersonalTrainerNeed = resultSet.getInt(IS_PERSONAL_TRAINER_NEED);
            order.setIsPersonalTrainerNeed(isPersonalTrainerNeed);

            BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN_LABEL);
            order.setPrice(price);

            int isPayed = resultSet.getInt(IS_PAYED_COLUMN_LABEL);
            order.setIsPayed(isPayed);

            String feedback = resultSet.getString(FEEDBACK_COLUMN_LABEL);
            order.setFeedback(feedback);

            return order;
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * This method initialize queries for common operations.
     *
     * @return Map object with queries.
     */
    @Override
    protected Map<String, String> initializeCommonQueries() {
        Map<String, String> commonQueries = new HashMap<>();

        commonQueries.put(SELECT_ALL_QUERY_KEY, SELECT_ALL_QUERY);
        commonQueries.put(SELECT_BY_ID_QUERY_KEY, SELECT_BY_ID_QUERY);
        commonQueries.put(DELETE_BY_ID_QUERY_KEY, DELETE_BY_ID_QUERY);
        commonQueries.put(INSERT_ENTITY_QUERY_KEY, INSERT_ENTITY_QUERY);
        commonQueries.put(UPDATE_ENTITY_QUERY_KEY, UPDATE_ENTITY_QUERY);

        return commonQueries;
    }
}
