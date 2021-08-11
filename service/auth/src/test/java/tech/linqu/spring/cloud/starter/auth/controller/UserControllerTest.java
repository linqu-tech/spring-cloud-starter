package tech.linqu.spring.cloud.starter.auth.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.linqu.spring.cloud.starter.tests.TestDataConst.UID_1;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import tech.linqu.spring.cloud.starter.auth.domain.UserStatus;
import tech.linqu.spring.cloud.starter.auth.tests.SchemaTablesProvider;
import tech.linqu.spring.cloud.starter.auth.tests.data.DataUser;
import tech.linqu.spring.cloud.starter.proto.external.user.UserCurrentRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserCurrentResponse;
import tech.linqu.spring.cloud.starter.proto.external.user.UserSignupRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserChangeStatusRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDataRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDeleteRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserListRequest;
import tech.linqu.spring.cloud.starter.tests.annotation.DataProvider;
import tech.linqu.spring.cloud.starter.tests.annotation.TeardownTables;
import tech.linqu.spring.cloud.starter.tests.tests.SpringBootMvcTests;
import tech.linqu.webpb.runtime.WebpbUtils;

@TeardownTables(SchemaTablesProvider.class)
class UserControllerTest extends SpringBootMvcTests {

    @Test
    void givenNoUsers_whenSignup_ShouldReturnSuccess() throws Exception {
        perform(request(new UserSignupRequest("user", "password")))
            .andExpect(status().isOk());
    }

    @DataProvider(value = { DataUser.User1.class })
    @Test
    void givenUserExists_whenSignup_ShouldReturnError() throws Exception {
        perform(request(new UserSignupRequest("USER_10001", "password")))
            .andExpect(status().isBadRequest());
    }

    @DataProvider(value = { DataUser.User1.class })
    @Test
    @WithMockUser(value = "USER_10001", authorities = "SCOPE_ui")
    void givenUserExists_whenGetCurrent_ShouldReturnUser() throws Exception {
        perform(request(new UserCurrentRequest()))
            .andExpect(status().isOk())
            .andExpect(result -> {
                UserCurrentResponse response = WebpbUtils.deserialize(
                    result.getResponse().getContentAsString(),
                    UserCurrentResponse.class
                );
                assertNotNull(response.getUser());
                assertEquals(UID_1, response.getUser().getId());
            });
    }

    @Test
    @WithMockUser("USER_10001")
    void givenWithoutScope_whenGetCurrent_ShouldReturn403() throws Exception {
        perform(request(new UserCurrentRequest()))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "USER_NOT_EXISTS", authorities = "SCOPE_ui")
    void givenUserNotExists_whenGetCurrent_ShouldReturnNullUser() throws Exception {
        perform(request(new UserCurrentRequest()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.user", is(nullValue())));
    }

    @DataProvider(value = { DataUser.User1.class, DataUser.User2.class })
    @Test
    @WithMockUser(authorities = "SCOPE_server")
    void givenUsers_whenListUsers_ShouldReturnUserList() throws Exception {
        perform(request(new RpcUserListRequest()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.users.length()", is(2)));
    }

    @DataProvider(value = { DataUser.User1.class })
    @Test
    @WithMockUser(authorities = "SCOPE_server")
    void givenUserExists_whenGetUser_ShouldReturnUser() throws Exception {
        perform(request(new RpcUserDataRequest(UID_1)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.user.id", is(UID_1 + "")));
    }

    @Test
    @WithMockUser(authorities = "SCOPE_server")
    void givenUserNotExists_whenGetUser_ShouldReturnError() throws Exception {
        perform(request(new RpcUserDataRequest(UID_1)))
            .andExpect(status().isNotFound());
    }

    @DataProvider(value = { DataUser.User1.class })
    @Test
    @WithMockUser(authorities = "SCOPE_server")
    void givenUserExists_whenChangeStatus_ShouldReturnNewStatus() throws Exception {
        perform(request(new RpcUserChangeStatusRequest(UID_1, UserStatus.INACTIVE.getValue())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(UserStatus.INACTIVE.getValue())));
    }

    @DataProvider(value = { DataUser.User1.class })
    @Test
    @WithMockUser(authorities = "SCOPE_server")
    void givenUserExists_whenDelete_ShouldReturnSuccess() throws Exception {
        perform(request(new RpcUserDeleteRequest(UID_1)))
            .andExpect(status().isOk());
    }
}
