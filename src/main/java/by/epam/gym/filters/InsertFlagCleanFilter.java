package by.epam.gym.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.epam.gym.commands.ActionCommand.COMMAND_PARAMETER;
import static by.epam.gym.commands.ActionCommand.IS_RECORD_INSERTED;

/**
 * Filter to clean database record flag.
 *
 * @author Eugene Makarenko
 */
public class InsertFlagCleanFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(InsertFlagCleanFilter.class);

    private static final String ADD_FEEDBACK_PAGE = "add_feedback.jsp";
    private static final String PREPARE_ORDER_PAGE = "prepare_order.jsp";
    private static final String REGISTER_PAGE = "register.jsp";
    private static final String CREATE_EXERCISE_PAGE = "create_exercise.jsp";

    private static final String EDIT_TRAINING_PROGRAM_COMMAND = "special_edit_training_program";
    private static final String CREATE_TRAINING_PROGRAM_COMMAND = "trainer_create_training_program";
    private static final String LOGIN_COMMAND = "common_login";

    private static final List<String> pages = new ArrayList<>();
    private static final List<String> commands = new ArrayList<>();

    static {
        pages.add(ADD_FEEDBACK_PAGE);
        pages.add(PREPARE_ORDER_PAGE);
        pages.add(REGISTER_PAGE);
        pages.add(CREATE_EXERCISE_PAGE);

        commands.add(EDIT_TRAINING_PROGRAM_COMMAND);
        commands.add(CREATE_TRAINING_PROGRAM_COMMAND);
        commands.add(LOGIN_COMMAND);
    }

    /**
     * This method initialize filters object.
     *
     * @param filterConfig the filters config.
     * @throws ServletException object if execution of method is failed.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * The method does main logic of filters.
     *
     * @param servletRequest  the servlet request.
     * @param servletResponse the servlet response.
     * @param filterChain     the filters chain of responsibility.
     * @throws IOException      object if execution of method is failed.
     * @throws ServletException object if execution of method is failed.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String currentPage = httpServletRequest.getServletPath();
        HttpSession session = httpServletRequest.getSession();
        for (String page : pages) {
            if (currentPage.endsWith(page)) {
                session.removeAttribute(IS_RECORD_INSERTED);
                LOGGER.info("Record flag was cleaned successful.");
                break;
            }
        }

        String currentCommand = httpServletRequest.getParameter(COMMAND_PARAMETER);
        if (currentCommand != null) {
            if (commands.contains(currentCommand)) {
                session.removeAttribute(IS_RECORD_INSERTED);
                LOGGER.info("Record flag was cleaned successful.");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * This method cleans filter resources.
     */
    @Override
    public void destroy() {

    }
}
