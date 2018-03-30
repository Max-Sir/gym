package by.epam.gym.service;

import by.epam.gym.dao.OrderDAOImpl;
import by.epam.gym.entities.order.Order;
import by.epam.gym.exceptions.ConnectionException;
import by.epam.gym.exceptions.DAOException;
import by.epam.gym.exceptions.ServiceException;

import java.util.HashMap;
import java.util.List;

public class OrderService {

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

}
