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

    /**
     * This method finds entity from database by id.
     *
     * @param trainingProgramId the entity's id.
     * @return the entity.
     * @throws ServiceException object if execution of query is failed.
     */
    public TrainingProgram findTrainingProgramById(int trainingProgramId) throws ServiceException {
        try(ConnectionManager<TrainingProgramDAOImpl> connectionManager = new ConnectionManager<>(TrainingProgramDAOImpl.class)) {
            TrainingProgramDAOImpl trainingProgramDAO = connectionManager.createDAO();
            TrainingProgram trainingProgram = trainingProgramDAO.findEntityById(trainingProgramId);

            return trainingProgram;
        } catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * This method finds last training program of trainer.
     *
     * @param authorId the author id.
     * @return the id of last created training program.
     * @throws ServiceException object if execution of query is failed.
     */
    public int lastTrainingProgramId(int authorId) throws ServiceException {
        try(ConnectionManager<TrainingProgramDAOImpl> connectionManager = new ConnectionManager<>(TrainingProgramDAOImpl.class)) {
            TrainingProgramDAOImpl trainingProgramDAO = connectionManager.createDAO();

            return trainingProgramDAO.findLastTrainingProgram(authorId);
        }catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    /**
     * This method inserts training program in database.
     *
     * @param trainingProgram the entity.
     * @return true if operation was made successfully and false otherwise.
     * @throws ServiceException object if execution of query is failed.
     */
    public boolean createTrainingProgram(TrainingProgram trainingProgram) throws ServiceException {
        try(ConnectionManager<TrainingProgramDAOImpl> connectionManager = new ConnectionManager<>(TrainingProgramDAOImpl.class)){
            TrainingProgramDAOImpl trainingProgramDAO = connectionManager.createDAO();

           return trainingProgramDAO.insert(trainingProgram);
        }catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }

    public int findClientTrainingProgramId(int clientId) throws ServiceException {
        try(ConnectionManager<TrainingProgramDAOImpl> connectionManager = new ConnectionManager<>(TrainingProgramDAOImpl.class)) {
            TrainingProgramDAOImpl trainingProgramDAO = connectionManager.createDAO();

            return trainingProgramDAO.findClientTrainingProgram(clientId);
        } catch (Exception exception) {
            throw new ServiceException("Exception detected. " + exception);
        }
    }
}
