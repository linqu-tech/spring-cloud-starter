package tech.linqu.spring.cloud.starter.auth.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWKMatcher;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.junit.jupiter.api.Test;

class AuthorizationConfigurationTest {

    @Test
    void shouldGetJwkSourceSuccess() throws KeySourceException {
        AuthorizationConfiguration configuration = new AuthorizationConfiguration();
        JWKSource<SecurityContext> source = configuration.jwkSource();
        JWKMatcher matcher = mock(JWKMatcher.class);
        JWKSelector selector = new JWKSelector(matcher);
        assertTrue(source.get(selector, null).isEmpty());
    }

    @Test
    void shouldGetProviderSettingsSuccess() {
        AuthorizationConfiguration configuration = new AuthorizationConfiguration();
        assertEquals("a.com/uaa", configuration.providerSettings("a.com").getIssuer());
    }
}
