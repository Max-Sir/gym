package by.epam.gym.service;

import by.epam.gym.dao.ExerciseDAOImpl;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.exceptions.ConnectionException;
import by.epam.gym.exceptions.ServiceException;

public class ExerciseService {

    public void addExerciseToDatabase(Exercise exercise) throws ServiceException {
        try(ConnectionManager<ExerciseDAOImpl> connectionManager = new ConnectionManager<>(ExerciseDAOImpl.class)) {
            ExerciseDAOImpl exerciseDAO = connectionManager.createDAO();

            exerciseDAO.insert(exercise);

        }  catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

}
