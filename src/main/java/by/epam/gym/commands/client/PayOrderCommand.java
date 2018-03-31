package by.epam.gym.commands.client;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.order.Order;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.OrderService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.PAY_ORDER_RESULT_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.ORDER_PAYED_ERROR_MESSAGE_PATH;
import static by.epam.gym.utils.MessageManager.ORDER_PAYED_SUCCESSFUL_MESSAGE_PATH;
import static by.epam.gym.utils.MessageManager.RESULT_ATTRIBUTE;

public class PayOrderCommand implements ActionCommand {

    private static final String ORDER_ATTRIBUTE = "order";

    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl;

        try{
            HttpSession session = request.getSession();
            Order order = (Order) session.getAttribute(ORDER_ATTRIBUTE);
            OrderService orderService = new OrderService();

            boolean isOperationSuccessful = orderService.payOrder(order);
            if (isOperationSuccessful){
                request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(ORDER_PAYED_SUCCESSFUL_MESSAGE_PATH));
            } else {
                request.setAttribute(RESULT_ATTRIBUTE,MessageManager.getProperty(ORDER_PAYED_ERROR_MESSAGE_PATH));
            }
            session.removeAttribute(ORDER_ATTRIBUTE);
            pageUrl = ConfigurationManager.getProperty(PAY_ORDER_RESULT_PAGE_PATH);
            page.setRedirect(false);
        }catch (ServiceException exception) {
        pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
        page.setRedirect(true);
    }
        page.setPageUrl(pageUrl);
        return page;
    }
}
