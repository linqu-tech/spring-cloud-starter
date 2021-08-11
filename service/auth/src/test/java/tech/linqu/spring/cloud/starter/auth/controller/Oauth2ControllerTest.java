package tech.linqu.spring.cloud.starter.auth.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import tech.linqu.spring.cloud.starter.auth.service.Oauth2Service;
import tech.linqu.spring.cloud.starter.proto.external.oauth2.Oauth2ConsentRequest;
import tech.linqu.spring.cloud.starter.proto.external.oauth2.Oauth2ConsentResponse;
import tech.linqu.spring.cloud.starter.tests.tests.SpringBootMvcTests;

@ContextConfiguration(name = "Oauth2ControllerTest")
class Oauth2ControllerTest extends SpringBootMvcTests {

    @MockBean
    Oauth2Service oauth2Service;

    @Test
    @WithMockUser(value = "USER_10001")
    void givenPrinciple_whenGetConsent_ShouldReturnSuccess() throws Exception {
        when(oauth2Service.getConsent(any(), any()))
            .thenReturn(new Oauth2ConsentResponse().setClientId("CLIENT"));
        perform(request(new Oauth2ConsentRequest("CLIENT", "SCOPE", "STATE")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.clientId", is("CLIENT")));
    }
}
