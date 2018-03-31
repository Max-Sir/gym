package by.epam.gym.service;

import by.epam.gym.dao.OrderDAOImpl;
import by.epam.gym.entities.order.Order;
import by.epam.gym.entities.order.OrderDurationType;
import by.epam.gym.exceptions.ConnectionException;
import by.epam.gym.exceptions.DAOException;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.utils.OrderCalculator;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public class OrderService {

    private static final int NOT_PAYED_ORDER_STATUS = 0;
    private static final int PAYED_ORDER_STATUS = 1;

    public List<Order> findAllClientOrder(int clientId) throws ServiceException {
        try(ConnectionManager<OrderDAOImpl> connectionManager = new ConnectionManager<>(OrderDAOImpl.class)) {
            OrderDAOImpl orderDAO = connectionManager.createDAO();

            return orderDAO.findAllClientOrder(clientId);
        } catch (ConnectionException | DAOException exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    public boolean addFeedback(int id, String feedback) throws ServiceException {
        try(ConnectionManager<OrderDAOImpl> connectionManager = new ConnectionManager<>(OrderDAOImpl.class)) {
            OrderDAOImpl orderDAO = connectionManager.createDAO();

            return orderDAO.addFeedback(id,feedback);
        } catch (ConnectionException | DAOException exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    public Order prepareOrder(int clientId,String purchaseDateValue, String durationValue, String isPersonalTrainerNeedValue){
        Date purchaseDate = Date.valueOf(purchaseDateValue);
        OrderDurationType duration = OrderDurationType.valueOf(durationValue);

        OrderCalculator orderCalculator = new OrderCalculator();
        Date endDate = orderCalculator.calculateEndDate(duration,purchaseDate);
        int isPersonalTrainerNeed = Integer.parseInt(isPersonalTrainerNeedValue);
        BigDecimal price = orderCalculator.calculatePrice(duration,isPersonalTrainerNeed);

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
    }

    public boolean payOrder(Order order) throws ServiceException {
        ConnectionManager<OrderDAOImpl> connectionManager = null;
        try {
            connectionManager = new ConnectionManager<>(OrderDAOImpl.class);
            connectionManager.startTransaction();

            order.setIsPayed(PAYED_ORDER_STATUS);

            OrderDAOImpl orderDAO = connectionManager.createDAO();
            boolean isOperationSuccessful = orderDAO.insert(order);

            if (!isOperationSuccessful){
                connectionManager.rollbackTransaction();
                return false;
            }

            connectionManager.commitTransaction();
            return true;
        } catch (ConnectionException | DAOException exception) {
            if (connectionManager != null){
                connectionManager.rollbackTransaction();
            }
            throw new ServiceException("Exception detected. " + exception);
        } finally {
            if (connectionManager != null){
                connectionManager.endTransaction();
                connectionManager.close();
            }
        }
    }

}
