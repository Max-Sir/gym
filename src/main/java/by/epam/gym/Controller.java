package by.epam.gym;

import by.epam.gym.commands.ActionCommand;
import by.epam.gym.commands.CommandFactory;
import by.epam.gym.utils.ConfigurationManager;
import by.epam.gym.utils.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.epam.gym.utils.MessageManager.NULL_PAGE_MESSAGE_PATH;

/**
 * MVC pattern controller class.
 *
 * @author Eugene Makarenko
 */
public class Controller extends HttpServlet {

    private static final String NULL_ATTRIBUTE = "nullPage";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        CommandFactory factory = new CommandFactory();
        ActionCommand command = factory.defineCommand(request);
        page = command.execute(request);
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager.MAIN_PAGE_PATH);
            HttpSession session = request.getSession();
            session.setAttribute(NULL_ATTRIBUTE, MessageManager.getProperty(NULL_PAGE_MESSAGE_PATH));
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
