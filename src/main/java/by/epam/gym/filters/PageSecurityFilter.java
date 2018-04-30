package by.epam.gym.filters;

import by.epam.gym.entities.user.User;
import by.epam.gym.entities.user.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.epam.gym.commands.ActionCommand.USER_ATTRIBUTE;

/**
 * Filter to follow user's role and pages.
 *
 * @author Eugene Makarenko
 */
public class PageSecurityFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(PageSecurityFilter.class);

    private static final String MAIN_PAGE_PARAMETER = "MAIN_PAGE";

    private static final String TRAINER_PAGE_PATH_PATTERN = ".*/jsp/trainer/.*.jsp";
    private static final String ADMIN_PAGE_PATH_PATTERN = ".*/jsp/admin/.*.jsp";
    private static final String CLIENT_PAGE_PATH_PATTERN = ".*/jsp/client/.*.jsp";
    private static final String COMMON_PAGE_PATH_PATTERN = ".*/jsp/common/.*.jsp*";
    private static final String SPECIAL_PAGE_PATH_PATTERN = ".*/jsp/special/.*.jsp*";

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
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String currentPage = httpServletRequest.getServletPath();
        boolean isCommonJsp = checkPath(currentPage, COMMON_PAGE_PATH_PATTERN);
        if (isCommonJsp) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpSession session = httpServletRequest.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            if (user == null) {
                LOGGER.warn(String.format("Unexpected action from guest, page=%s.", currentPage));

                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + redirectPage);
            } else {
                UserRole currentRole = user.getUserRole();
                boolean isUserRightRole = checkRole(currentRole, currentPage);
                boolean isSpecialJsp = checkPath(currentPage, SPECIAL_PAGE_PATH_PATTERN);

                if (!isUserRightRole && !isSpecialJsp) {
                    LOGGER.warn(String.format("Unexpected action from user id=%d, page=%s.", user.getId(), currentPage));

                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + redirectPage);
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        }
    }

    @Override
    public void destroy() {

    }

    private boolean checkRole(UserRole userRole, String pagePath) {
        switch (userRole) {
            case TRAINER: {
                return checkPath(pagePath, TRAINER_PAGE_PATH_PATTERN);
            }
            case CLIENT: {
                return checkPath(pagePath, CLIENT_PAGE_PATH_PATTERN);
            }
            case ADMIN: {
                return checkPath(pagePath, ADMIN_PAGE_PATH_PATTERN);
            }
            default: {
                return false;
            }
        }
    }

    private boolean checkPath(String path, String pagePattern) {
        Pattern pattern = Pattern.compile(pagePattern);
        Matcher matcher = pattern.matcher(path);

        return matcher.matches();
    }
}
