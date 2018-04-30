package by.epam.gym.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.epam.gym.commands.ActionCommand.COMMAND_PARAMETER;
import static by.epam.gym.commands.ActionCommand.IS_RECORD_INSERTED;

/**
 * Filter to follow database records.
 *
 * @author Eugene Makarenko
 */
public class CommandDatabaseRecordFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(CommandDatabaseRecordFilter.class);

    private static final String REDIRECT_PAGE_PATH = "REDIRECT_PAGE";

    private static final String REGISTER_COMMAND = "common_register";
    private static final String ADD_FEEDBACK_COMMAND = "client_add_feedback";
    private static final String PAY_ORDER_COMMAND = "client_pay_order";
    private static final String REFUSE_TRAINING_PROGRAM_COMMAND = "client_refuse_training_program";
    private static final String SAVE_TRAINING_PROGRAM_EDIT_COMMAND = "special_save_training_program_edit";
    private static final String FINISH_TRAINING_PROGRAM_CREATION_COMMAND = "trainer_finish_training_program_creation";
    private static final String CREATE_EXERCISE_COMMAND = "trainer_create_exercise";

    private static final List<String> commands = new ArrayList<>();

    static {
        commands.add(REGISTER_COMMAND);
        commands.add(ADD_FEEDBACK_COMMAND);
        commands.add(PAY_ORDER_COMMAND);
        commands.add(REFUSE_TRAINING_PROGRAM_COMMAND);
        commands.add(SAVE_TRAINING_PROGRAM_EDIT_COMMAND);
        commands.add(FINISH_TRAINING_PROGRAM_CREATION_COMMAND);
        commands.add(CREATE_EXERCISE_COMMAND);
    }

    private String redirectPage;

    /**
     * This method initialize filters object.
     *
     * @param filterConfig the filters config.
     * @throws ServletException object if execution of method is failed.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        redirectPage = filterConfig.getInitParameter(REDIRECT_PAGE_PATH);
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
        String currentCommand = httpServletRequest.getParameter(COMMAND_PARAMETER);
        HttpSession session = httpServletRequest.getSession();

        if (!commands.contains(currentCommand)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (session.getAttribute(IS_RECORD_INSERTED) != null) {
                LOGGER.info(String.format("Command - %s can't be invoked again.", currentCommand));

                session.removeAttribute(IS_RECORD_INSERTED);
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + redirectPage);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    /**
     * This method cleans filter resources.
     */
    @Override
    public void destroy() {

    }
}
