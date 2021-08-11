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

package tech.linqu.spring.cloud.starter.account.mapper;

import org.mapstruct.Mapper;
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
import tech.linqu.spring.cloud.starter.utilities.mapstruct.GeneralMapper;

/**
 * Mapping between proto messages.
 */
@Mapper(componentModel = "spring", uses = { GeneralMapper.class })
public interface ProtoMapper {

    RpcUserListRequest mapping(UserListRequest request);

    UserListResponse mapping(RpcUserListResponse response);

    RpcUserDataRequest mapping(UserDataRequest request);

    UserDataResponse mapping(RpcUserDataResponse response);

    RpcUserChangeStatusRequest mapping(UserChangeStatusRequest request);

    UserChangeStatusResponse mapping(RpcUserChangeStatusResponse response);

    RpcUserDeleteRequest mapping(UserDeleteRequest request);

    UserDeleteResponse mapping(RpcUserDeleteResponse response);
}
