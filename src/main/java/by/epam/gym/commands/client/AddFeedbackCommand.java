package by.epam.gym.commands.client;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.exceptions.ServiceException;
import by.epam.gym.service.OrderService;
import by.epam.gym.servlet.Page;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.gym.utils.ConfigurationManager.ADD_FEEDBACK_PAGE_PATH;
import static by.epam.gym.utils.ConfigurationManager.ERROR_PAGE_PATH;
import static by.epam.gym.utils.MessageManager.FEEDBACK_ADDED_MESSAGE_PATH;
import static by.epam.gym.utils.MessageManager.FEEDBACK_WAS_NOT_ADDED_MESSAGE_PATH;
import static by.epam.gym.utils.MessageManager.RESULT_ATTRIBUTE;

public class AddFeedbackCommand implements ActionCommand {

    private static final String ORDER_ID_ATTRIBUTE = "orderId";

    private static final String FEEDBACK_PARAMETER = "feedback";

    @Override
    public Page execute(HttpServletRequest request) {
        Page page = new Page();
        String pageUrl;

        try{
            HttpSession session = request.getSession();
            int orderId = (int) session.getAttribute(ORDER_ID_ATTRIBUTE);

            OrderService orderService = new OrderService();
            String feedback = request.getParameter(FEEDBACK_PARAMETER);

            boolean isOperationSuccessful = orderService.addFeedback(orderId,feedback);

            if (isOperationSuccessful){
                pageUrl = ConfigurationManager.getProperty(ADD_FEEDBACK_PAGE_PATH);
                request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(FEEDBACK_ADDED_MESSAGE_PATH));
            } else {
                pageUrl = ConfigurationManager.getProperty(ADD_FEEDBACK_PAGE_PATH);
                request.setAttribute(RESULT_ATTRIBUTE, MessageManager.getProperty(FEEDBACK_WAS_NOT_ADDED_MESSAGE_PATH));
            }

            page.setRedirect(false);
        }catch (ServiceException exception) {
            pageUrl = ConfigurationManager.getProperty(ERROR_PAGE_PATH);
            page.setRedirect(true);
        }

        page.setPageUrl(pageUrl);
        return page;
    }
}
