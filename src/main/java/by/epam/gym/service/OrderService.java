package by.epam.gym.service;

import by.epam.gym.dao.ConnectionManager;
import by.epam.gym.dao.OrderDAOImpl;
import by.epam.gym.entities.order.Order;
import by.epam.gym.entities.order.OrderDurationType;
import by.epam.gym.exceptions.ConnectionException;
import by.epam.gym.exceptions.DAOException;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.utils.OrderCalculator;
import by.epam.gym.utils.OrderDataValidator;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static by.epam.gym.service.DiscountService.NONE_DISCOUNT;

/**
 * Service class for entity Order.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.order.Order
 * @see by.epam.gym.dao.OrderDAOImpl
 * @see by.epam.gym.dao.ConnectionManager
 */
public class OrderService {

    private static final Logger LOGGER = Logger.getLogger(OrderService.class);

    private static final int NOT_PAYED_ORDER_STATUS = 0;
    private static final int PAYED_ORDER_STATUS = 1;

    /**
     * This method checks client for having actual order.
     *
     * @param clientIdValue the client's id string value.
     * @return true if client has actual order and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean hasClientActualOrder(String clientIdValue) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            OrderDAOImpl orderDAO = new OrderDAOImpl(connectionManager.getConnection());
            int clientId = Integer.parseInt(clientIdValue);

            return orderDAO.hasClientActualOrder(clientId);
        } catch (ConnectionException | DAOException exception) {
            LOGGER.warn("Exception during <hasClientActualOrder> operation.");
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * This method adds feedback about order.
     *
     * @param orderId  the order's id.
     * @param feedback the feedback
     * @return true if operation was successful and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean addFeedback(String feedback, int orderId) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            OrderDataValidator orderDataValidator = new OrderDataValidator();
            boolean isDataValid = orderDataValidator.checkFeedback(feedback);
            if (!isDataValid) {
                return false;
            }

            OrderDAOImpl orderDAO = new OrderDAOImpl(connectionManager.getConnection());
            return orderDAO.updateFeedback(feedback, orderId);
        } catch (ConnectionException | DAOException exception) {
            LOGGER.warn("Exception during <add feedback> operation.");
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * This methods inserts order into database.
     *
     * @param order the Order object.
     * @return true if operation was successful and false otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean payOrder(Order order) throws ServiceException {
        ConnectionManager connectionManager = null;
        try {
            connectionManager = new ConnectionManager();
            connectionManager.startTransaction();

            order.setIsPayed(PAYED_ORDER_STATUS);

            OrderDAOImpl orderDAO = new OrderDAOImpl(connectionManager.getConnection());
            boolean isOperationSuccessful = orderDAO.insert(order);

            if (!isOperationSuccessful) {
                connectionManager.rollbackTransaction();
                return false;
            }

            connectionManager.commitTransaction();
            return true;
        } catch (ConnectionException | DAOException exception) {
            LOGGER.warn("Exception during <pay order> operation.");
            if (connectionManager != null) {
                connectionManager.rollbackTransaction();
            }
            throw new ServiceException("Exception detected. " + exception);
        } finally {
            if (connectionManager != null) {
                connectionManager.endTransaction();
                connectionManager.close();
            }
        }
    }

    /**
     * This method creates Order object from input parameters.
     *
     * @param clientId                   the client id.
     * @param purchaseDateValue          the purchase date.
     * @param durationValue              the duration.
     * @param isPersonalTrainerNeedValue is personal trainer need int value.
     * @return Order object.
     * @throws ServiceException object if execution of method is failed.
     */
    public Order prepareOrder(int clientId, String purchaseDateValue, String durationValue, String isPersonalTrainerNeedValue) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {

            Date purchaseDate = Date.valueOf(purchaseDateValue);
            OrderDurationType duration = OrderDurationType.valueOf(durationValue);
            OrderCalculator orderCalculator = new OrderCalculator();
            Date endDate = orderCalculator.calculateEndDate(duration, purchaseDate);

            OrderDAOImpl orderDAO = new OrderDAOImpl(connectionManager.getConnection());
            int isPersonalTrainerNeed = Integer.parseInt(isPersonalTrainerNeedValue);
            BigDecimal price = orderDAO.selectPriceForOrder(duration, isPersonalTrainerNeed);

            DiscountService discountService = new DiscountService();
            int discount = discountService.getDiscount(clientId);

            if (discount != NONE_DISCOUNT) {
                price = orderCalculator.calculatePrice(price, discount);
            }

            Order order = new Order();
            order.setClientId(clientId);
            order.setPurchaseDate(purchaseDate);
            order.setEndDate(endDate);
            order.setDuration(duration);
            order.setIsPersonalTrainerNeed(isPersonalTrainerNeed);
            order.setPrice(price);
            order.setIsPayed(NOT_PAYED_ORDER_STATUS);
            order.setFeedback(null);

            return order;
        } catch (ConnectionException | DAOException exception) {
            LOGGER.warn("Exception during <prepare order> operation.");
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * This method finds all orders of client.
     *
     * @param clientId the client's id.
     * @return List of orders.
     * @throws ServiceException object if execution of method is failed.
     */
    public List<Order> findAllClientOrder(int clientId) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            OrderDAOImpl orderDAO = new OrderDAOImpl(connectionManager.getConnection());

            return orderDAO.selectClientOrders(clientId);
        } catch (ConnectionException | DAOException exception) {
            LOGGER.warn("Exception during <find client order> operation.");
            throw new ServiceException("Exception detected. " + exception);
        }
    }

}
