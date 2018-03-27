package by.epam.gym.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PageRedirectSecurityFilter implements Filter {

    private static final String MAIN_PAGE_PARAMETER = "MAIN_PATH";

    private String mainPage;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        mainPage = filterConfig.getInitParameter(MAIN_PAGE_PARAMETER);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + mainPage);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
