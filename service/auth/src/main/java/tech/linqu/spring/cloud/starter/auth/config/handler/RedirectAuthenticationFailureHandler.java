package tech.linqu.spring.cloud.starter.auth.config.handler;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * Redirect on authentication failure.
 */
public class RedirectAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * Construct a handler.
     *
     * @param defaultFailureUrl default failure redirect url
     */
    public RedirectAuthenticationFailureHandler(String defaultFailureUrl) {
        super(defaultFailureUrl);
        setRedirectStrategy(new PreserveRedirectStrategy());
    }
}
