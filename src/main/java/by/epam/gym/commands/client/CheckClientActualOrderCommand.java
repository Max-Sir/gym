package by.epam.gym.commands.client;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.order.Order;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.OrderService;
import by.epam.gym.servlet.Page;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.gym.utils.MessageManager.CLIENT_HAS_ALREADY_ORDER_MESSAGE_KEY;

/**
 * Command to check client for having actual order before making new.
 *
 * @author Eugene Makarenko
 * @see Order
 * @see OrderService
 * @see ActionCommand
 */
public class CheckClientActualOrderCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(CheckClientActualOrderCommand.class);

    /**
     * Implementation of command to check client for having actual order before making new.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        try {
            String clientIdValue = request.getParameter(CLIENT_ID_PARAMETER);
            OrderService orderService = new OrderService();
            boolean hasClientActualOrder = orderService.hasClientActualOrder(clientIdValue);

            if (hasClientActualOrder) {
                return new Page(Page.MAIN_PAGE_PATH, false, CLIENT_HAS_ALREADY_ORDER_MESSAGE_KEY);
            }

            return new Page(Page.PREPARE_ORDER_PAGE_PATH, false);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }
}
