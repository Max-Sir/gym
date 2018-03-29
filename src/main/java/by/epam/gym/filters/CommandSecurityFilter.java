package by.epam.gym.filters;

import by.epam.gym.entities.user.User;
import by.epam.gym.entities.user.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandSecurityFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(CommandSecurityFilter.class);

    private static final String ERROR_PAGE_PARAMETER = "ERROR_PAGE";

    private static final String USER_SESSION_ATTRIBUTE = "user";

    private static final String TRAINER_COMMAND_PATTERN = "trainer_";
    private static final String ADMIN_COMMAND_PATTERN = "admin_";
    private static final String CLIENT_COMMAND_PATTERN = "client_";
    private static final String COMMON_COMMAND_PATTER = "common_";

    private String errorPage;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        errorPage = filterConfig.getInitParameter(ERROR_PAGE_PARAMETER);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String currentCommand = httpServletRequest.getParameter("command");

        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(USER_SESSION_ATTRIBUTE);

        if (!currentCommand.startsWith(COMMON_COMMAND_PATTER)){
            if (user != null) {
                UserRole userRole = user.getUserRole();
                boolean isAccessTrue = checkRole(userRole, currentCommand);

                if (!isAccessTrue) {
                    LOGGER.warn(String.format("Unexpected action from user id=%d, command=%s.",user.getId(),currentCommand));

                    RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(errorPage);
                    requestDispatcher.forward(servletRequest,servletResponse);
                }
            } else if (!currentCommand.startsWith(COMMON_COMMAND_PATTER)){
                LOGGER.warn(String.format("Unexpected action from guest, command=%s.",currentCommand));

                RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(errorPage);
                requestDispatcher.forward(servletRequest,servletResponse);
            }
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }

    private boolean checkRole(UserRole userRole, String command){
        switch (userRole){
            case TRAINER:{
                return command.startsWith(TRAINER_COMMAND_PATTERN);
            }
            case CLIENT:{
                return command.startsWith(CLIENT_COMMAND_PATTERN);
            }
            case ADMIN:{
                return command.startsWith(ADMIN_COMMAND_PATTERN);
            }
            default:{
                return false;
            }
        }
    }
}
