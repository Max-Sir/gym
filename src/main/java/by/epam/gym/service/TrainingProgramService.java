package by.epam.gym.service;

import by.epam.gym.dao.ConnectionManager;
import by.epam.gym.dao.ExerciseDAOImpl;
import by.epam.gym.dao.TrainingProgramDAOImpl;
import by.epam.gym.dao.UserDAOImpl;
import by.epam.gym.entities.TrainingProgram;
import by.epam.gym.entities.exercise.Exercise;
import by.epam.gym.exceptions.DAOException;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.utils.TrainingProgramDataValidator;

import java.sql.Date;
import java.util.*;

/**
 * Service class for TrainingProgram entity.
 *
 * @see by.epam.gym.entities.TrainingProgram
 * @see by.epam.gym.dao.TrainingProgramDAOImpl
 */
public class TrainingProgramService {

    private static final int DAY_INCREMENT_INDEX = 1;

    /**
     * This method refuses training program.
     *
     * @param trainingProgramId the training program id.
     * @return true if operation was successful and false otherwise.
     * @throws ServiceException object if execution of query is failed.
     */
    public boolean refuseTrainingProgram(int trainingProgramId) throws ServiceException {

        ConnectionManager connectionManager = new ConnectionManager();
        try {
            connectionManager.startTransaction();

            TrainingProgramDAOImpl trainingProgramDAO = new TrainingProgramDAOImpl(connectionManager.getConnection());

            boolean isExercisesCleaned = trainingProgramDAO.deleteExercisesFromTrainingProgram(trainingProgramId);
            if (!isExercisesCleaned) {
                connectionManager.rollbackTransaction();
                return false;
            }

            boolean isTrainingProgramDeleted = trainingProgramDAO.deleteById(trainingProgramId);
            if (!isTrainingProgramDeleted) {
                connectionManager.rollbackTransaction();
                return false;
            }

            connectionManager.commitTransaction();
            return true;
        } catch (DAOException exception) {
            connectionManager.rollbackTransaction();

            throw new ServiceException("Exception during refuse training program operation.", exception);
        } finally {
            connectionManager.endTransaction();
            connectionManager.close();
        }
    }

    /**
     * This method saves TrainingProgram object and gets its id.
     *
     * @param trainingProgram the training program.
     * @return training program's id.
     * @throws ServiceException object if execution of query is failed.
     */
    public int saveTrainingProgram(TrainingProgram trainingProgram) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            TrainingProgramDAOImpl trainingProgramDAO = new TrainingProgramDAOImpl(connectionManager.getConnection());

            return trainingProgramDAO.insertTrainingProgram(trainingProgram);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during save training program operation.", exception);
        }
    }

    /**
     * This method creates training program.
     *
     * @param authorId       the author id.
     * @param clientIdValue  the client id valued.
     * @param diet           the diet.
     * @param startDateValue the start date value.
     * @param endDateValue   the end date value.
     * @return TrainingProgram object.
     * @throws ServiceException object if execution of query is failed.
     */
    public TrainingProgram createTrainingProgram(int authorId, String clientIdValue, String diet, String startDateValue, String endDateValue) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            TrainingProgram trainingProgram = new TrainingProgram();
            trainingProgram.setAuthorId(authorId);

            int clientId = Integer.parseInt(clientIdValue);
            trainingProgram.setClientId(clientId);

            Integer personalTrainerId = null;
            TrainingProgramDAOImpl trainingProgramDAO = new TrainingProgramDAOImpl(connectionManager.getConnection());
            boolean isPersonalClientNeed = trainingProgramDAO.isClientNeedPersonalTrainer(clientId);
            if (isPersonalClientNeed) {
                personalTrainerId = authorId;
            }
            trainingProgram.setPersonalTrainerId(personalTrainerId);

            trainingProgram.setDiet(diet);

            Date startDate = Date.valueOf(startDateValue);
            trainingProgram.setStartDate(startDate);

            Date endDate = Date.valueOf(endDateValue);
            trainingProgram.setEndDate(endDate);

            return trainingProgram;
        } catch (DAOException exception) {
            throw new ServiceException("Exception during create training program operation.", exception);
        }
    }

    /**
     * This method creates days and exercises for training program.
     *
     * @param daysCountValue the days count value.
     * @return TreeMap object with day number and exercises in that day.
     */
    public TreeMap<Integer, List<Exercise>> getDaysAndExerciseFromTrainingProgram(String daysCountValue) {
        int daysCount = Integer.parseInt(daysCountValue);

        TreeMap<Integer, List<Exercise>> daysAndExercises = new TreeMap<>();
        for (int index = 1; index <= daysCount; index++) {
            List<Exercise> list = new ArrayList<>();
            daysAndExercises.put(index, list);
        }

        return daysAndExercises;
    }

    /**
     * This method updates training program.
     *
     * @param trainingProgram the training program.
     * @return true if operation was successful and false otherwise.
     * @throws ServiceException object if execution of query is failed.
     */
    public boolean updateTrainingProgram(TrainingProgram trainingProgram) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            TrainingProgramDAOImpl trainingProgramDAO = new TrainingProgramDAOImpl(connectionManager.getConnection());

            return trainingProgramDAO.update(trainingProgram);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during update training program operation.", exception);
        }
    }

    /**
     * This method finds entity from database by client's id.
     *
     * @param clientId the client's id.
     * @return the entity.
     * @throws ServiceException object if execution of query is failed.
     */
    public TrainingProgram findTrainingProgramById(int clientId) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            TrainingProgramDAOImpl trainingProgramDAO = new TrainingProgramDAOImpl(connectionManager.getConnection());

            return trainingProgramDAO.selectClientTrainingProgram(clientId);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during find training program by id operation.", exception);
        }
    }

    /**
     * This method finds training program author.
     *
     * @param trainingProgramId the training program id.
     * @return the name of author.
     * @throws ServiceException object if execution of method is failed.
     */
    public String findTrainingProgramAuthorName(int trainingProgramId) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            UserDAOImpl userDAO = new UserDAOImpl(connectionManager.getConnection());

            return userDAO.selectTrainingProgramAuthorName(trainingProgramId);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during find training program author name operation.", exception);
        }
    }

    /**
     * This method shows exercises from training program.
     *
     * @param trainingProgramId the training program id.
     * @return TreeMap of exercises.
     * @throws ServiceException object if execution of method is failed.
     */
    public TreeMap<Integer, List<Exercise>> showExercisesFromTrainingProgram(int trainingProgramId) throws ServiceException {
        try (ConnectionManager connectionManager = new ConnectionManager()) {
            ExerciseDAOImpl exerciseDAO = new ExerciseDAOImpl(connectionManager.getConnection());

            return exerciseDAO.selectExerciseFromTrainingProgram(trainingProgramId);
        } catch (DAOException exception) {
            throw new ServiceException("Exception during show exercises from training program operation.", exception);
        }
    }

    /**
     * This method deletes day from training program.
     *
     * @param dayNumberValue   the day number value.
     * @param daysAndExercises the days and exercise in this days.
     * @return TreeMap with new days and exercises.
     */
    public TreeMap<Integer, List<Exercise>> deleteDayFromTrainingProgram(String dayNumberValue, TreeMap<Integer, List<Exercise>> daysAndExercises) {
        int dayNumber = Integer.parseInt(dayNumberValue);
        TreeMap<Integer, List<Exercise>> newDaysAndExercises = new TreeMap<>();

        Set<Map.Entry<Integer, List<Exercise>>> mapSet = daysAndExercises.entrySet();
        for (Map.Entry<Integer, List<Exercise>> entry : mapSet) {
            int day = entry.getKey();
            List<Exercise> exercises = entry.getValue();

            if (day < dayNumber) {
                newDaysAndExercises.put(day, exercises);
            } else if (day > dayNumber) {
                int currentDay = --day;
                newDaysAndExercises.put(currentDay, exercises);
            }
        }
        return newDaysAndExercises;
    }

    /**
     * This method adds day in training program.
     *
     * @param daysAndExercises the days and exercise in this days.
     * @return true if operation was successful and false otherwise.
     */
    public boolean addDayInTrainingProgram(TreeMap<Integer, List<Exercise>> daysAndExercises) {
        TrainingProgramDataValidator trainingProgramDataValidator = new TrainingProgramDataValidator();
        boolean isDaysCountValid = trainingProgramDataValidator.checkDaysCountForAddOperation(daysAndExercises);
        if (!isDaysCountValid) {
            return false;
        }

        int currentDaysCount = daysAndExercises.size();
        int day = currentDaysCount + DAY_INCREMENT_INDEX;

        List<Exercise> exercises = new ArrayList<>();
        daysAndExercises.put(day, exercises);

        return true;
    }
}
