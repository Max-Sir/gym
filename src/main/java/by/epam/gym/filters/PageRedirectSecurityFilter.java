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

public class PageRedirectSecurityFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(PageRedirectSecurityFilter.class);

    private static final String MAIN_PAGE_PARAMETER = "MAIN_PAGE";

    private static final String USER_SESSION_ATTRIBUTE = "user";

    private static final String TRAINER_PAGE_PATH_PATTERN = ".*/jsp/trainer/.*.jsp";
    private static final String ADMIN_PAGE_PATH_PATTERN = ".*/jsp/admin/.*.jsp";
    private static final String CLIENT_PAGE_PATH_PATTERN = ".*/jsp/client/.*.jsp";
    private static final String COMMON_PAGE_PATH_PATTERN = ".*/jsp/common/.*.jsp*";

    private String mainPage;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        mainPage = filterConfig.getInitParameter(MAIN_PAGE_PARAMETER);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String currentPage = httpServletRequest.getServletPath();

        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(USER_SESSION_ATTRIBUTE);

        boolean isCommonJsp = checkPath(currentPage,COMMON_PAGE_PATH_PATTERN);

        if(!isCommonJsp){

            if (user == null){
                LOGGER.warn(String.format("Unexpected action from guest, page=%s.",currentPage));

                httpServletResponse.sendRedirect(mainPage);
            } else {
                UserRole currentRole = user.getUserRole();
                boolean isUserRightRole = checkRole(currentRole,currentPage);

                if (!isUserRightRole){
                    LOGGER.warn(String.format("Unexpected action from user id=%d, page=%s.", user.getId(),currentPage));

                    httpServletResponse.sendRedirect(mainPage);
                }
            }

        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }

    private boolean checkRole(UserRole userRole, String pagePath){
        switch (userRole){
            case TRAINER:{
                return checkPath(pagePath, TRAINER_PAGE_PATH_PATTERN);
            }
            case CLIENT:{
                return checkPath(pagePath,CLIENT_PAGE_PATH_PATTERN);
            }
            case ADMIN:{
                return checkPath(pagePath,ADMIN_PAGE_PATH_PATTERN);
            }
            default:{
                return false;
            }
        }
    }

    private boolean checkPath(String path, String pagePattern){
        Pattern pattern = Pattern.compile(pagePattern);
        Matcher matcher = pattern.matcher(path);

        return matcher.matches();
    }
}
