package by.epam.gym.commands.client;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.order.Order;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.OrderService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.OrderDataValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.gym.servlet.Page.PAY_ORDER_PAGE_PATH;
import static by.epam.gym.servlet.Page.PREPARE_ORDER_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.INVALID_INPUT_DATA_MESSAGE_KEY;

/**
 * Command to prepare order.
 *
 * @author Eugene Makarenko
 * @see Order
 * @see OrderService
 * @see ActionCommand
 */
public class PrepareOrderCommand implements ActionCommand {

    private static final Logger LOGGER = Logger.getLogger(PrepareOrderCommand.class);

    /**
     * Implementation of command to prepare order.
     *
     * @param request HttpServletRequest object.
     * @return page.
     */
    @Override
    public Page execute(HttpServletRequest request) {

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            int clientId = user.getId();

            String purchaseDateValue = request.getParameter(PURCHASE_DATE_PARAMETER);
            String durationValue = request.getParameter(DURATION_PARAMETER);
            String isPersonalTrainerNeedValue = request.getParameter(IS_PERSONAL_TRAINER_NEED_PARAMETER);

            OrderDataValidator orderDataValidator = new OrderDataValidator();
            boolean isDataValid = orderDataValidator.checkOrderData(purchaseDateValue, durationValue, isPersonalTrainerNeedValue);
            if (!isDataValid) {
                LOGGER.info(String.format("Data: %s, %s, %s is not valid", purchaseDateValue, durationValue, isPersonalTrainerNeedValue));
                return new Page(PREPARE_ORDER_PAGE_PATH, false, INVALID_INPUT_DATA_MESSAGE_KEY);
            }

            OrderService orderService = new OrderService();
            Order order = orderService.prepareOrder(clientId, purchaseDateValue, durationValue, isPersonalTrainerNeedValue);
            session.setAttribute(ORDER_ATTRIBUTE, order);

            return new Page(PAY_ORDER_PAGE_PATH, false);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }
}