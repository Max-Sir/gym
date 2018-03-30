package by.epam.gym.dao;

import by.epam.gym.entities.order.Order;
import by.epam.gym.entities.order.OrderDurationType;
import by.epam.gym.exceptions.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that provide access to the database and deal with Order entity.
 *
 * @author Eugene Makarenko
 * @see AbstractDAOImpl
 * @see Order
 */
public class OrderDAOImpl extends AbstractDAOImpl<Order>{

    private static final String CLIENT_ID_COLUMN_LABEL = "client_id";
    private static final String PURCHASE_DATE_COLUMN_LABEL = "purchase_date";
    private static final String END_DATE_COLUMN_LABEL = "end_date";
    private static final String DURATION_COLUMN_LABEL = "duration";
    private static final String IS_PERSONAL_TRAINER_NEED = "is_personal_trainer_need";
    private static final String PRICE_COLUMN_LABEL = "price";
    private static final String IS_PAYED_COLUMN_LABEL = "is_payed";
    private static final String FEEDBACK_COLUMN_LABEL = "feedback";

    private static final String ORDERS_RESOURCES_FILE_NAME = "orders";

    /**
     * Instantiates a new UserDAOImpl.
     *
     * @param connection   the connection to database.
     */
    public OrderDAOImpl(Connection connection) {
        super(connection, ORDERS_RESOURCES_FILE_NAME);
    }

    public List<Order> findAllClientOrder(int clientId) throws DAOException {
        String sqlQuery = resourceBundle.getString("query.find_all_client_orders");
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, clientId);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();

            while (resultSet.next()){
                Order order = buildEntity(resultSet);

                orders.add(order);
            }

            return orders;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    public boolean addFeedback(int id, String feedback) throws DAOException {
        String sqlQuery = resourceBundle.getString("query.add_feedback");
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1,feedback);
            preparedStatement.setInt(2,id);

            int result = preparedStatement.executeUpdate();

            return result == SUCCESSFUL_RESULT;
        }catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }

    /**
     * This method builds Order object from ResultSet object.
     *
     * @param resultSet the result set of statement.
     * @return the Order object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public Order buildEntity(ResultSet resultSet) throws DAOException {
        try {
            Order order = new Order();

            String durationValue = resultSet.getString(DURATION_COLUMN_LABEL);

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            int clientId = resultSet.getInt(CLIENT_ID_COLUMN_LABEL);
            Date purchaseDate = resultSet.getDate(PURCHASE_DATE_COLUMN_LABEL);
            Date endDate = resultSet.getDate(END_DATE_COLUMN_LABEL);
            OrderDurationType duration = OrderDurationType.valueOf(durationValue);
            int isPersonalTrainerNeed = resultSet.getInt(IS_PERSONAL_TRAINER_NEED);
            BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN_LABEL);
            int isPayed = resultSet.getInt(IS_PAYED_COLUMN_LABEL);
            String feedback = resultSet.getString(FEEDBACK_COLUMN_LABEL);

            order.setId(id);
            order.setClientId(clientId);
            order.setPurchaseDate(purchaseDate);
            order.setEndDate(endDate);
            order.setDuration(duration);
            order.setIsPersonalTrainerNeed(isPersonalTrainerNeed);
            order.setPrice(price);
            order.setIsPayed(isPayed);
            order.setFeedback(feedback);

           return order;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }
}
