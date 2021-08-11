package tech.linqu.spring.cloud.starter.account.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import tech.linqu.spring.cloud.starter.account.client.AuthServiceClient;
import tech.linqu.spring.cloud.starter.account.mapper.ProtoMapper;
import tech.linqu.spring.cloud.starter.account.tests.SpringBootTestConfiguration;
import tech.linqu.spring.cloud.starter.proto.external.user.UserChangeStatusRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserDataRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserDeleteRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserListRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserChangeStatusResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDataResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDeleteResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserListResponse;
import tech.linqu.spring.cloud.starter.tests.tests.SpringComponentTests;

@Import(SpringBootTestConfiguration.class)
class UserServiceTest extends SpringComponentTests {

    @MockBean
    private AuthServiceClient authServiceClient;

    @Autowired
    private ProtoMapper protoMapper;

    private UserService userService;

    @BeforeAll
    void beforeAll() {
        this.userService = new UserService(authServiceClient, protoMapper);
    }

    @Test
    void shouldListUsersSuccess() {
        when(authServiceClient.request(any(), any()))
            .thenReturn(new RpcUserListResponse().setUsers(Collections.emptyList()));
        assertNotNull(this.userService.listUsers(new UserListRequest()));
    }

    @Test
    void shouldGetUserSuccess() {
        when(authServiceClient.request(any(), any()))
            .thenReturn(new RpcUserDataResponse());
        assertNotNull(this.userService.getUser(new UserDataRequest()));
    }

    @Test
    void shouldChangeStatusSuccess() {
        when(authServiceClient.request(any(), any()))
            .thenReturn(new RpcUserChangeStatusResponse());
        assertNotNull(this.userService.changeStatus(new UserChangeStatusRequest()));
    }

    @Test
    void shouldDeleteSuccess() {
        when(authServiceClient.request(any(), any()))
            .thenReturn(new RpcUserDeleteResponse());
        assertNotNull(this.userService.delete(new UserDeleteRequest()));
    }
}
