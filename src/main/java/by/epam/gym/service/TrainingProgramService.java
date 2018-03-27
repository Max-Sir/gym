package by.epam.gym.service;

import by.epam.gym.dao.TrainingProgramDAOImpl;
import by.epam.gym.entities.TrainingProgram;
import by.epam.gym.exceptions.ConnectionException;
import by.epam.gym.exceptions.ServiceException;

/**
 * Service class for Training program entity.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.TrainingProgram
 * @see ConnectionManager
 * @see by.epam.gym.dao.TrainingProgramDAOImpl
 */
public class TrainingProgramService {

    public TrainingProgram findTrainingProgramById(int trainingProgramId) throws ServiceException {
        try(ConnectionManager<TrainingProgramDAOImpl> connectionManager = new ConnectionManager<>(TrainingProgramDAOImpl.class)) {
            TrainingProgramDAOImpl trainingProgramDAO = connectionManager.createDAO();
            TrainingProgram trainingProgram = trainingProgramDAO.findEntityById(trainingProgramId);

            return trainingProgram;
        } catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }


    }

}
