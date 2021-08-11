package tech.linqu.spring.cloud.starter.auth.config.handler;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.util.StringUtils;

/**
 * Redirect by parameter.
 */
public class ParameterRedirectStrategy extends DefaultRedirectStrategy {

    /**
     * Send redirect if with redirect parameter.
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param url      url
     * @throws IOException throw {@link IOException}
     */
    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
        throws IOException {
        String redirect = request.getParameter("redirect");
        if (StringUtils.hasText(redirect)) {
            super.sendRedirect(request, response, redirect);
        } else {
            super.sendRedirect(request, response, url);
        }
    }
}
