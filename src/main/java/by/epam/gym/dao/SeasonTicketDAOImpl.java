package by.epam.gym.dao;

import by.epam.gym.entities.seasonticket.SeasonTicket;
import by.epam.gym.entities.seasonticket.SeasonTicketDurationType;
import by.epam.gym.exceptions.DAOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that provide access to the database and deal with SeasonTicket entity.
 *
 * @author Eugene Makarenko
 * @see AbstractDAOImpl
 * @see SeasonTicket
 */
public class SeasonTicketDAOImpl extends AbstractDAOImpl<SeasonTicket> {

    private static final String START_DATE_COLUMN_LABEL = "start_date";
    private static final String END_DATE_COLUMN_LABEL = "end_date";
    private static final String DURATION_COLUMN_LABEL = "duration";
    private static final String IS_PERSONAL_TRAINER_NEED_COLUMN_LABEL = "is_personal_trainer_need";

    private static final String SEASON_TICKETS_RESOURCES_FILE_NAME = "season_tickets";

    /**
     * Instantiates a new SeasonTicketDAOImpl.
     *
     * @param connection   the connection to database.
     */
    public SeasonTicketDAOImpl(Connection connection) {
        super(connection, SEASON_TICKETS_RESOURCES_FILE_NAME);
    }

    /**
     * This method builds SeasonTicket object from ResultSet object.
     *
     * @param resultSet the result set of statement.
     * @return The SeasonTicket object.
     * @throws DAOException object if execution of query is failed.
     */
    @Override
    public SeasonTicket buildEntity(ResultSet resultSet) throws DAOException {
        try {
            SeasonTicket seasonTicket = new SeasonTicket();

            String durationValue = resultSet.getString(DURATION_COLUMN_LABEL);

            int id = resultSet.getInt(ID_COLUMN_LABEL);
            Date startDate  = resultSet.getDate(START_DATE_COLUMN_LABEL);
            Date endDate = resultSet.getDate(END_DATE_COLUMN_LABEL);
            SeasonTicketDurationType duration = SeasonTicketDurationType.valueOf(durationValue);
            int isPersonalTrainerNeed = resultSet.getInt(IS_PERSONAL_TRAINER_NEED_COLUMN_LABEL);

            seasonTicket.setId(id);
            seasonTicket.setStart(startDate);
            seasonTicket.setEnd(endDate);
            seasonTicket.setDuration(duration);
            seasonTicket.setPersonalTrainerNeed(isPersonalTrainerNeed);

            return seasonTicket;
        } catch (SQLException exception) {
            throw new DAOException("SQL exception detected. " + exception);
        }
    }
}
