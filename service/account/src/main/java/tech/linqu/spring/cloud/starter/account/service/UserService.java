/*
 * Copyright (c) 2020 linqu.tech, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.linqu.spring.cloud.starter.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.linqu.spring.cloud.starter.account.client.AuthServiceClient;
import tech.linqu.spring.cloud.starter.account.mapper.ProtoMapper;
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

/**
 * User service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthServiceClient authServiceClient;

    private final ProtoMapper protoMapper;

    /**
     * List users.
     *
     * @param request {@link UserListRequest}
     * @return {@link UserListResponse}
     */
    public UserListResponse listUsers(UserListRequest request) {
        RpcUserListRequest rpcRequest = protoMapper.mapping(request);
        RpcUserListResponse response = authServiceClient
            .request(rpcRequest, RpcUserListResponse.class);
        return protoMapper.mapping(response);
    }

    /**
     * Get user.
     *
     * @param request {@link UserDataRequest}
     * @return {@link UserDataResponse}
     */
    public UserDataResponse getUser(UserDataRequest request) {
        RpcUserDataRequest rpcRequest = protoMapper.mapping(request);
        RpcUserDataResponse response = authServiceClient
            .request(rpcRequest, RpcUserDataResponse.class);
        return protoMapper.mapping(response);
    }

    /**
     * Change status.
     *
     * @param request {@link UserChangeStatusRequest}
     * @return {@link UserChangeStatusResponse}
     */
    public UserChangeStatusResponse changeStatus(UserChangeStatusRequest request) {
        RpcUserChangeStatusRequest rpcRequest = protoMapper.mapping(request);
        RpcUserChangeStatusResponse response = authServiceClient
            .request(rpcRequest, RpcUserChangeStatusResponse.class);
        return protoMapper.mapping(response);
    }

    /**
     * Delete a user.
     *
     * @param request {@link UserDeleteRequest}
     * @return {@link UserDeleteResponse}
     */
    public UserDeleteResponse delete(UserDeleteRequest request) {
        RpcUserDeleteRequest rpcRequest = protoMapper.mapping(request);
        RpcUserDeleteResponse response = authServiceClient
            .request(rpcRequest, RpcUserDeleteResponse.class);
        return protoMapper.mapping(response);
    }
}
