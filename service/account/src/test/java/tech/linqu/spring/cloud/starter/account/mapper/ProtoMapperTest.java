package tech.linqu.spring.cloud.starter.account.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import tech.linqu.spring.cloud.starter.proto.external.user.UserChangeStatusRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserChangeStatusResponse;
import tech.linqu.spring.cloud.starter.proto.external.user.UserDataRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserDataResponse;
import tech.linqu.spring.cloud.starter.proto.external.user.UserDeleteRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserDeleteResponse;
import tech.linqu.spring.cloud.starter.proto.external.user.UserListRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserListResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserChangeStatusRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserChangeStatusResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDataRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDataResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDeleteRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDeleteResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserListRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserListResponse;

class ProtoMapperTest {

    private final ProtoMapper protoMapper = new ProtoMapperImpl();

    @Test
    void shouldMapUserListRequestSuccess() {
        assertNull(protoMapper.mapping((UserListRequest) null));

        UserListRequest message = new UserListRequest();
        RpcUserListRequest request = protoMapper.mapping(message);
        assertNull(request.getGenders());

        request = protoMapper.mapping(message.setGenders(Collections.emptyList()));
        assertNotNull(request.getGenders());
    }

    @Test
    void shouldMapRpcUserListResponseSuccess() {
        assertNull(protoMapper.mapping((RpcUserListResponse) null));

        RpcUserListResponse message = new RpcUserListResponse();
        UserListResponse request = protoMapper.mapping(message);
        assertNull(request.getUsers());

        request = protoMapper.mapping(message.setUsers(Collections.emptyList()));
        assertNotNull(request.getUsers());
    }

    @Test
    void shouldMapUserDataRequestSuccess() {
        assertNull(protoMapper.mapping((UserDataRequest) null));

        UserDataRequest message = new UserDataRequest();
        RpcUserDataRequest request = protoMapper.mapping(message);
        assertNotNull(request);
    }

    @Test
    void shouldMapRpcUserDataResponseSuccess() {
        assertNull(protoMapper.mapping((RpcUserDataResponse) null));

        RpcUserDataResponse message = new RpcUserDataResponse();
        UserDataResponse request = protoMapper.mapping(message);
        assertNotNull(request);
    }

    @Test
    void shouldMapUserChangeStatusRequestSuccess() {
        assertNull(protoMapper.mapping((UserChangeStatusRequest) null));

        UserChangeStatusRequest message = new UserChangeStatusRequest();
        RpcUserChangeStatusRequest request = protoMapper.mapping(message);
        assertNotNull(request);
    }

    @Test
    void shouldMapRpcUserChangeStatusResponseSuccess() {
        assertNull(protoMapper.mapping((RpcUserChangeStatusResponse) null));

        RpcUserChangeStatusResponse message = new RpcUserChangeStatusResponse();
        UserChangeStatusResponse request = protoMapper.mapping(message);
        assertNotNull(request);
    }

    @Test
    void shouldMapUserDeleteRequestSuccess() {
        assertNull(protoMapper.mapping((UserDeleteRequest) null));

        UserDeleteRequest message = new UserDeleteRequest();
        RpcUserDeleteRequest request = protoMapper.mapping(message);
        assertNotNull(request);
    }

    @Test
    void shouldMapRpcUserDeleteResponseSuccess() {
        assertNull(protoMapper.mapping((RpcUserDeleteResponse) null));

        RpcUserDeleteResponse message = new RpcUserDeleteResponse();
        UserDeleteResponse request = protoMapper.mapping(message);
        assertNotNull(request);
    }
}
