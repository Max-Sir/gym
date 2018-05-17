package by.epam.gym.commands.client;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.OrderService;
import by.epam.gym.commands.Page;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.gym.commands.Page.ADD_FEEDBACK_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.FEEDBACK_WAS_ADDED_MESSAGE_KEY;
import static by.epam.gym.utils.MessageManager.FEEDBACK_WAS_NOT_ADDED_MESSAGE_KEY;

/**
 * Command to add feedback about order.
 *
 * @author Eugene Makarenko
 * @see by.epam.gym.entities.order.Order
 * @see OrderService
 */
public class AddFeedbackCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(AddFeedbackCommand.class);

    /**
     * Implementation of command to add feedback about order.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {

        try {
            HttpSession session = request.getSession();
            int orderId = (int) session.getAttribute(ORDER_ID_ATTRIBUTE);
            String feedback = request.getParameter(FEEDBACK_PARAMETER);

            OrderService orderService = new OrderService();
            boolean isOperationSuccessful = orderService.addFeedback(feedback, orderId);

            if (!isOperationSuccessful) {
                return new Page(ADD_FEEDBACK_PAGE_PATH, false, FEEDBACK_WAS_NOT_ADDED_MESSAGE_KEY);
            }

            session.setAttribute(IS_RECORD_INSERTED, true);

            return new Page(Page.MAIN_PAGE_PATH, false, FEEDBACK_WAS_ADDED_MESSAGE_KEY);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }
}