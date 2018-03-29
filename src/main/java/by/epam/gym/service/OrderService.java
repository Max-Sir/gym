package by.epam.gym.service;

import by.epam.gym.dao.OrderDAOImpl;
import by.epam.gym.entities.Order;
import by.epam.gym.exceptions.ServiceException;

import java.util.List;

public class OrderService {

    public List<Order> findAllClientOrder(int clientId) throws ServiceException {
        try(ConnectionManager<OrderDAOImpl> connectionManager = new ConnectionManager<>(OrderDAOImpl.class)) {
            OrderDAOImpl orderDAO = connectionManager.createDAO();

            return orderDAO.findAllClientOrder(clientId);
        }catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

}
