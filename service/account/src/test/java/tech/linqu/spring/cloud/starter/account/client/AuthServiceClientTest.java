package tech.linqu.spring.cloud.starter.account.client;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.linqu.spring.cloud.starter.proto.exception.ApiException;
import tech.linqu.spring.cloud.starter.proto.imports.error.ErrorMessage;
import tech.linqu.webpb.runtime.WebpbUtils;

class AuthServiceClientTest {

    @Test
    void shouldCreateAuthServiceClientSuccess() {
        assertDoesNotThrow(() -> new AuthServiceClient(WebClient.builder(), ""));
    }

    @Test
    void shouldCreateExceptionSuccess() {
        AuthServiceClient client = new AuthServiceClient(WebClient.builder(), "");
        ClientResponse clientResponse = mock(ClientResponse.class);
        doReturn(Mono.just(WebpbUtils.serialize(new ErrorMessage(0, "Error")).getBytes()))
            .when(clientResponse).bodyToMono(byte[].class);
        ApiException exception = (ApiException) client.createException(clientResponse).block();
        assertNotNull(exception);
        assertEquals(0, exception.getErrorValue());
    }

    @Test
    void givenErrorCodeInvalid_shouldCreateExceptionSuccess() {
        AuthServiceClient client = new AuthServiceClient(WebClient.builder(), "");
        ClientResponse clientResponse = mock(ClientResponse.class);
        doReturn(Mono.just(WebpbUtils.serialize(new ErrorMessage(-1, "Error")).getBytes()))
            .when(clientResponse).bodyToMono(byte[].class);
        ApiException exception = (ApiException) client.createException(clientResponse).block();
        assertNotNull(exception);
        assertNull(exception.getErrorCode());
    }
}
