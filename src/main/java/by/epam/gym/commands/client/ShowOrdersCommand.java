package by.epam.gym.commands.client;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.order.Order;
import by.epam.gym.entities.user.User;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.OrderService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.SHOW_CLIENT_ORDERS_PAGE_PATH;

public class ShowOrdersCommand implements ActionCommand {

    private static final String LIST_ATTRIBUTE = "list";
    private static final String USER_ATTRIBUTE = "user";
    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl;

        try{
            OrderService orderService = new OrderService();

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            int clientId = user.getId();
            List<Order> orderList = orderService.findAllClientOrder(clientId);

            request.setAttribute(LIST_ATTRIBUTE,orderList);
            pageUrl = ConfigurationManager.getProperty(SHOW_CLIENT_ORDERS_PAGE_PATH);
            page.setRedirect(false);
        }catch (ServiceException exception) {
            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }

        page.setPageUrl(pageUrl);
        return page;
    }
}
