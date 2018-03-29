package by.epam.gym.dao;

import by.epam.gym.entities.Order;
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
    private static final String SEASON_TICKET_ID_COLUMN_LABEL = "season_ticket_id";
    private static final String PURCHASE_DATE_COLUMN_LABEL = "purchase_date";
    private static final String PRICE_COLUMN_LABEL = "price";
    private static final String IS_PAYED_COLUMN_LABEL = "is_payed";

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

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            int clientId = resultSet.getInt(CLIENT_ID_COLUMN_LABEL);
            int seasonTicketId = resultSet.getInt(SEASON_TICKET_ID_COLUMN_LABEL);
            Date date = resultSet.getDate(PURCHASE_DATE_COLUMN_LABEL);
            BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN_LABEL);
            int isPayed = resultSet.getInt(IS_PAYED_COLUMN_LABEL);

            order.setId(id);
            order.setClientId(clientId);
            order.setSeasonTicketId(seasonTicketId);
            order.setPurchaseDate(date);
            order.setPrice(price);
            order.setIsPayed(isPayed);

           return order;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }
}
