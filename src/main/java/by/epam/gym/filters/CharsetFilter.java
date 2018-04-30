package by.epam.gym.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter to encode parameters for utf-8.
 *
 * @author Eugene Makarenko
 */
public class CharsetFilter implements Filter {

    private static final String ENCODING_TYPE_PARAMETER = "encodingType";

    private String code;

    /**
     * This method initialize filters object.
     *
     * @param filterConfig the filters config.
     * @throws ServletException object if execution of method is failed.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        code = filterConfig.getInitParameter(ENCODING_TYPE_PARAMETER);
    }

    /**
     * The method does main logic of filters.
     *
     * @param request  the servlet request.
     * @param response the servlet response.
     * @param chain    the filters chain of responsibility.
     * @throws IOException      object if execution of method is failed.
     * @throws ServletException object if execution of method is failed.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }

    /**
     * The method destroys filters.
     */
    @Override
    public void destroy() {
    }
}