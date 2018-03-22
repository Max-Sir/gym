package by.epam.gym.dao;

import by.epam.gym.entities.Entity;
import by.epam.gym.exceptions.DAOException;

import java.util.List;

public interface DAO <T extends Entity> {

    List<T> findAll() throws DAOException;

    T findEntityById(int id) throws DAOException;

    void deleteById(int id) throws DAOException;

    void insert(T entity) throws DAOException;

    void update(T entity) throws DAOException;

}
