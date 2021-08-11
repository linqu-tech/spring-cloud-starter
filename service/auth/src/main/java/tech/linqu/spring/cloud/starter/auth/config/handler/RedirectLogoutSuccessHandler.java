package tech.linqu.spring.cloud.starter.auth.config.handler;

import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

/**
 * Redirect on logout success.
 */
public class RedirectLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    /**
     * Construct a handler.
     */
    public RedirectLogoutSuccessHandler() {
        setRedirectStrategy(new ParameterRedirectStrategy());
    }
}
