package by.epam.gym.service;

import by.epam.gym.dao.ConnectionManager;
import by.epam.gym.dao.OrderDAOImpl;
import by.epam.gym.entities.order.Order;
import by.epam.gym.exceptions.DAOException;
import by.epam.gym.exceptions.ServiceException;

import java.util.List;

/**
 * Service class to get discount for client.
 */
public class DiscountService {

    public static final int NONE_DISCOUNT = 0;

    private static final int MINIMAL_ORDERS_COUNT_FOR_DISCOUNT = 5;
    private static final int MIDDLE_ORDERS_COUNT_FOR_DISCOUNT = 10;
    private static final int MAX_ORDERS_COUNT_FOR_DISCOUNT = 15;

    private static final int MINIMAL_DISCOUNT_PERCENT = 5;
    private static final int MIDDLE_DISCOUNT_PERCENT = 10;
    private static final int MAX_DISCOUNT_PERCENT = 15;

    /**
     * This method returns discount.
     *
     * @param clientId the client's id.
     * @return the discount.
     */
    public int getDiscount(int clientId) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            OrderDAOImpl orderDAO = new OrderDAOImpl(connectionManager.getConnection());
            List<Order> clientOrders = orderDAO.selectClientOrders(clientId);
            int ordersCount = clientOrders.size();

            if (ordersCount == MINIMAL_ORDERS_COUNT_FOR_DISCOUNT || (ordersCount > MINIMAL_ORDERS_COUNT_FOR_DISCOUNT && ordersCount < MIDDLE_ORDERS_COUNT_FOR_DISCOUNT)) {
                return MINIMAL_DISCOUNT_PERCENT;
            }

            if (ordersCount == MIDDLE_ORDERS_COUNT_FOR_DISCOUNT || (ordersCount > MIDDLE_ORDERS_COUNT_FOR_DISCOUNT && ordersCount < MAX_ORDERS_COUNT_FOR_DISCOUNT)) {
                return MIDDLE_DISCOUNT_PERCENT;
            }

            if (ordersCount >= MAX_ORDERS_COUNT_FOR_DISCOUNT) {
                return MAX_DISCOUNT_PERCENT;
            }

            return NONE_DISCOUNT;
        } catch (DAOException exception) {
            throw new ServiceException("Exception during get discount operation.", exception);
        }
    }
}
