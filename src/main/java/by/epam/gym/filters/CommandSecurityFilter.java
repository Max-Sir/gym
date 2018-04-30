package by.epam.gym.filters;

import by.epam.gym.entities.user.User;
import by.epam.gym.entities.user.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.epam.gym.commands.ActionCommand.COMMAND_PARAMETER;
import static by.epam.gym.commands.ActionCommand.USER_ATTRIBUTE;

/**
 * Filter to follow user's role command.
 *
 * @author Eugene Makarenko
 */
public class CommandSecurityFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(CommandSecurityFilter.class);

    private static final String MAIN_PAGE_PARAMETER = "MAIN_PAGE";

    private static final String TRAINER_COMMAND_PATTERN = "trainer_";
    private static final String ADMIN_COMMAND_PATTERN = "admin_";
    private static final String CLIENT_COMMAND_PATTERN = "client_";
    private static final String COMMON_COMMAND_PATTERN = "common_";
    private static final String SPECIAL_COMMAND_PATTERN = "special_";

    private String redirectPage;

    /**
     * This method initialize filters object.
     *
     * @param filterConfig the filters config.
     * @throws ServletException object if execution of method is failed.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        redirectPage = filterConfig.getInitParameter(MAIN_PAGE_PARAMETER);
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
        User user = (User) session.getAttribute(USER_ATTRIBUTE);

        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        if (currentCommand.startsWith(COMMON_COMMAND_PATTERN)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (user == null) {
                LOGGER.warn(String.format("Unexpected action from guest, command=%s.", currentCommand));

                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + redirectPage);
            } else {
                UserRole userRole = user.getUserRole();
                boolean isAccessTrue = checkRole(userRole, currentCommand);

                if (!isAccessTrue) {
                    LOGGER.warn(String.format("Unexpected action from user id=%d, command=%s.", user.getId(), currentCommand));

                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + redirectPage);
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        }
    }


    /**
     * This method cleans filter resources.
     */
    @Override
    public void destroy() {

    }

    private boolean checkRole(UserRole userRole, String command) {

        if (command.startsWith(SPECIAL_COMMAND_PATTERN)) {
            return true;
        }

        switch (userRole) {
            case TRAINER: {
                return command.startsWith(TRAINER_COMMAND_PATTERN);
            }
            case CLIENT: {
                return command.startsWith(CLIENT_COMMAND_PATTERN);
            }
            case ADMIN: {
                return command.startsWith(ADMIN_COMMAND_PATTERN);
            }
            default: {
                return false;
            }
        }
    }
}