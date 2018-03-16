package by.epam.gym.dao;

import by.epam.gym.entities.Order;
import by.epam.gym.exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * The class of DAO level that provide access to the database and deal with Order entity.
 *
 * @author Eugene Makarenko
 * @see AbstractDAO
 * @see Connection
 * @see ResultSet
 * @see List
 * @see Order
 */
public class OrderDAO extends AbstractDAO<Integer,Order> {

    /**
     * Instantiates a new OrderDAO.
     *
     * @param connection the connection to database.
     */
    public OrderDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Order> findAll() throws DAOException {
        return null;
    }

    @Override
    public Order findEntityById(Integer id) throws DAOException {
        return null;
    }

    @Override
    public boolean delete(Integer id) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(Order entity) throws DAOException {
        return false;
    }

    @Override
    public boolean create(Order entity) throws DAOException {
        return false;
    }

    @Override
    public boolean update(Order entity) throws DAOException {
        return false;
    }

    @Override
    public Order createEntity(ResultSet resultSet) throws DAOException {
        return null;
    }
}
