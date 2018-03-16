package by.epam.gym.dao;

import by.epam.gym.entities.subscription.Subscription;
import by.epam.gym.exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * The class of DAO level that provide access to the database and deal with Subscription entity.
 *
 * @author Eugene Makarenko
 * @see AbstractDAO
 * @see Connection
 * @see ResultSet
 * @see List
 * @see Subscription
 */
public class SubscriptionDAO extends AbstractDAO<Integer, Subscription> {

    /**
     * Instantiates a new SubscriptionDAO.
     *
     * @param connection the connection to database.
     */
    public SubscriptionDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Subscription> findAll() throws DAOException {
        return null;
    }

    @Override
    public Subscription findEntityById(Integer id) throws DAOException {
        return null;
    }

    @Override
    public boolean delete(Integer id) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(Subscription entity) throws DAOException {
        return false;
    }

    @Override
    public boolean create(Subscription entity) throws DAOException {
        return false;
    }

    @Override
    public boolean update(Subscription entity) throws DAOException {
        return false;
    }

    @Override
    public Subscription createEntity(ResultSet resultSet) throws DAOException {
        return null;
    }
}
