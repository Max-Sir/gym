package by.epam.gym.servlet;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.commands.CommandFactory;
import by.epam.gym.commands.Page;
import by.epam.gym.utils.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.epam.gym.commands.ActionCommand.MESSAGE_ATTRIBUTE;
import static by.epam.gym.utils.MessageManager.NONE_MESSAGE_KEY;

/**
 * MVC pattern controller class.
 *
 * @author Eugene Makarenko
 * @see Page
 * @see ActionCommand
 * @see HttpServletResponse
 * @see HttpServletRequest
 */
public class Controller extends HttpServlet {

    /**
     * Get method.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response.
     * @throws ServletException object if execution of method is failed.
     * @throws IOException      object if execution of method is failed.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Post method.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response.
     * @throws ServletException object if execution of method is failed.
     * @throws IOException      object if execution of method is failed.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Page page;
        CommandFactory factory = new CommandFactory();
        ActionCommand command = factory.defineCommand(request);
        page = command.execute(request);

        boolean isRedirect = page.isRedirect();
        if (isRedirect) {
            redirect(page, request, response);
        } else {
            forward(page, request, response);
        }
    }

    private void redirect(Page page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = page.getPageUrl();
        response.sendRedirect(request.getContextPath() + url);
    }

    private void forward(Page page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = page.getPageUrl();
        String messageKey = page.getMessageKey();
        if (!NONE_MESSAGE_KEY.equals(messageKey)) {
            String message = MessageManager.getProperty(messageKey);
            request.setAttribute(MESSAGE_ATTRIBUTE, message);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
    }

}
