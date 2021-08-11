package tech.linqu.spring.cloud.starter.auth.config.handler;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * Redirect on authentication success.
 */
public class RedirectAuthenticationSuccessHandler extends
    SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * Construct a handler.
     */
    public RedirectAuthenticationSuccessHandler() {
        setRedirectStrategy(new ParameterRedirectStrategy());
    }
}
