package by.epam.gym.commands.client;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.entities.order.Order;
import by.epam.gym.entities.user.User;
import by.epam.gym.service.OrderService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PrepareOrderCommand implements ActionCommand {

    private static final String PURCHASE_DATE_PARAMETER = "startDate";
    private static final String DURATION_PARAMETER = "duration";
    private static final String IS_PERSONAL_TRAINER_NEED_PARAMETER = "isPersonalTrainerNeed";

    private static final String USER_ATTRIBUTE = "user";
    private static final String ORDER_ATTRIBUTE = "order";

    @Override
    public Page execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        int clientId = user.getId();

        String purchaseDateValue = request.getParameter(PURCHASE_DATE_PARAMETER);
        String durationValue = request.getParameter(DURATION_PARAMETER);
        String isPersonalTrainerNeedValue = request.getParameter(IS_PERSONAL_TRAINER_NEED_PARAMETER);

        OrderService orderService = new OrderService();
        Order order = orderService.prepareOrder(clientId,purchaseDateValue,durationValue,isPersonalTrainerNeedValue);

        session.setAttribute(ORDER_ATTRIBUTE, order);

        Page page = new Page();
        String pageUrl = ConfigurationManager.getProperty(ConfigurationManager.PAY_ORDER_PAGE_PATH);
        page.setRedirect(false);
        page.setPageUrl(pageUrl);

        return page;
    }
}
