package tech.linqu.spring.cloud.starter.account.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import tech.linqu.spring.cloud.starter.account.tests.SchemaTablesProvider;
import tech.linqu.spring.cloud.starter.account.tests.SpringBootTestConfiguration;
import tech.linqu.spring.cloud.starter.proto.external.user.UserChangeStatusRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserDataRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserDeleteRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserListRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserChangeStatusRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserChangeStatusResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDataRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDataResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDeleteRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDeleteResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserListRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserListResponse;
import tech.linqu.spring.cloud.starter.tests.annotation.TeardownTables;
import tech.linqu.spring.cloud.starter.tests.tests.SpringBootMvcTests;
import tech.linqu.webpb.runtime.WebpbMessage;
import tech.linqu.webpb.runtime.WebpbMeta;
import tech.linqu.webpb.runtime.WebpbUtils;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Import(SpringBootTestConfiguration.class)
@TeardownTables(SchemaTablesProvider.class)
class UserControllerTest extends SpringBootMvcTests {

    private static ClientAndServer authService;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        authService = startClientAndServer(0);
        registry.add("starter.base-url.auth-service",
            () -> "http://localhost:" + authService.getPort());
    }

    @AfterAll
    static void afterAll() {
        authService.stop();
    }

    private void mockAuthService(WebpbMessage request, WebpbMessage response) {
        WebpbMeta meta = request.webpbMeta();
        String url = WebpbUtils.formatUrl(request);
        authService.when(
            HttpRequest.request()
                .withMethod(meta.getMethod())
                .withPath(url.split("\\?")[0]),
            exactly(1))
            .respond(response()
                .withStatusCode(200)
                .withBody(WebpbUtils.serialize(response))
            );
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ui")
    void givenAuthService_whenListUsers_ShouldReturnUserList() throws Exception {
        mockAuthService(new RpcUserListRequest(),
            new RpcUserListResponse(Collections.emptyList(), null));
        try (
            MockedStatic<ServletOAuth2AuthorizedClientExchangeFilterFunction> filter = mockStatic(
                ServletOAuth2AuthorizedClientExchangeFilterFunction.class)) {
            filter.when(() -> ServletOAuth2AuthorizedClientExchangeFilterFunction
                .clientRegistrationId(anyString()))
                .thenReturn((Consumer<Map<String, Object>>) map -> {
                });
            perform(request(new UserListRequest()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users.length()", is(0)));
        }
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ui")
    void givenAuthService_whenGetUser_ShouldReturnUser() throws Exception {
        mockAuthService(new RpcUserDataRequest(123L), new RpcUserDataResponse());
        try (
            MockedStatic<ServletOAuth2AuthorizedClientExchangeFilterFunction> filter = mockStatic(
                ServletOAuth2AuthorizedClientExchangeFilterFunction.class)) {
            filter.when(() -> ServletOAuth2AuthorizedClientExchangeFilterFunction
                .clientRegistrationId(anyString()))
                .thenReturn((Consumer<Map<String, Object>>) map -> {
                });
            perform(request(new UserDataRequest(123L))).andExpect(status().isOk());
        }
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ui")
    void givenAuthService_whenChangeStatus_ShouldReturnNewStatus() throws Exception {
        mockAuthService(new RpcUserChangeStatusRequest(123L, 1),
            new RpcUserChangeStatusResponse(1));
        try (
            MockedStatic<ServletOAuth2AuthorizedClientExchangeFilterFunction> filter = mockStatic(
                ServletOAuth2AuthorizedClientExchangeFilterFunction.class)) {
            filter.when(() -> ServletOAuth2AuthorizedClientExchangeFilterFunction
                .clientRegistrationId(anyString()))
                .thenReturn((Consumer<Map<String, Object>>) map -> {
                });
            perform(request(new UserChangeStatusRequest(123L, 1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)));
        }
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ui")
    void givenAuthService_whenDeleteUser_ShouldSuccess() throws Exception {
        mockAuthService(new RpcUserDeleteRequest(123L), new RpcUserDeleteResponse());
        try (
            MockedStatic<ServletOAuth2AuthorizedClientExchangeFilterFunction> filter = mockStatic(
                ServletOAuth2AuthorizedClientExchangeFilterFunction.class)) {
            filter.when(() -> ServletOAuth2AuthorizedClientExchangeFilterFunction
                .clientRegistrationId(anyString()))
                .thenReturn((Consumer<Map<String, Object>>) map -> {
                });
            perform(request(new UserDeleteRequest(123L))).andExpect(status().isOk());
        }
    }
}
