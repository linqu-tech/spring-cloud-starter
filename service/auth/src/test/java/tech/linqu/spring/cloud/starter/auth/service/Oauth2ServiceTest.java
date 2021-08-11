package tech.linqu.spring.cloud.starter.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import tech.linqu.spring.cloud.starter.proto.exception.ApiException;
import tech.linqu.spring.cloud.starter.proto.external.oauth2.Oauth2ConsentRequest;
import tech.linqu.spring.cloud.starter.proto.external.oauth2.Oauth2ConsentResponse;

class Oauth2ServiceTest {

    private final OAuth2AuthorizationConsentService consentService =
        mock(OAuth2AuthorizationConsentService.class);

    private final RegisteredClientRepository clientRepository =
        mock(RegisteredClientRepository.class);

    private final Oauth2Service oauth2Service = new Oauth2Service(consentService, clientRepository);

    @Test
    void shouldGetConsentSuccess() {
        Principal principal = mock(Principal.class);
        when(clientRepository.findByClientId(any())).thenReturn(mock(RegisteredClient.class));
        Oauth2ConsentRequest request = new Oauth2ConsentRequest().setClientId("CLIENT");
        Oauth2ConsentResponse response = oauth2Service.getConsent(principal, request);
        assertNotNull(response);
        assertEquals("CLIENT", response.getClientId());
    }

    @Test
    void givenClientNotExists_shouldThrowException() {
        Principal principal = mock(Principal.class);
        Oauth2ConsentRequest request = new Oauth2ConsentRequest().setClientId("CLIENT");
        assertThrows(ApiException.class, () -> oauth2Service.getConsent(principal, request));
    }

    @Test
    void givenAuthorizationConsent_shouldGetConsentSuccess() {
        Principal principal = mock(Principal.class);
        when(clientRepository.findByClientId(any())).thenReturn(mock(RegisteredClient.class));
        Oauth2ConsentRequest request = new Oauth2ConsentRequest()
            .setClientId("CLIENT")
            .setScope("scope1 scope2");
        OAuth2AuthorizationConsent consent = mock(OAuth2AuthorizationConsent.class);
        when(consent.getScopes()).thenReturn(new HashSet<>(Collections.singletonList("scope1")));
        when(consentService.findById(any(), any())).thenReturn(consent);

        Oauth2ConsentResponse response = oauth2Service.getConsent(principal, request);
        assertNotNull(response);
        assertEquals("CLIENT", response.getClientId());
    }
}
