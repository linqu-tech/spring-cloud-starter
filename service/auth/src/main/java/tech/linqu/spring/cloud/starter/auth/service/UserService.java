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

package tech.linqu.spring.cloud.starter.auth.service;

import static tech.linqu.spring.cloud.starter.proto.external.error.ErrorCode.SIGNUP_ERROR;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.linqu.spring.cloud.starter.auth.domain.UserStatus;
import tech.linqu.spring.cloud.starter.auth.mapper.ProtoMapper;
import tech.linqu.spring.cloud.starter.auth.repository.UserRepository;
import tech.linqu.spring.cloud.starter.auth.schema.tables.records.UserRecord;
import tech.linqu.spring.cloud.starter.proto.exception.ApiException;
import tech.linqu.spring.cloud.starter.proto.external.user.UserCurrentResponse;
import tech.linqu.spring.cloud.starter.proto.external.user.UserSignupRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserSignupResponse;
import tech.linqu.spring.cloud.starter.proto.internal.error.RpcErrorCode;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserChangeStatusRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserChangeStatusResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDataRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDataResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDeleteRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDeleteResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserListRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserListResponse;
import tech.linqu.spring.cloud.starter.proto.mapstruct.GeneralProtoMapper;
import tech.linqu.spring.cloud.starter.utilities.uid.UidGenerator;

/**
 * User service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UidGenerator uidGenerator;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final GeneralProtoMapper generalProtoMapper;

    private final ProtoMapper protoMapper;

    /**
     * Signup.
     *
     * @param request {@link UserSignupRequest}
     * @return {@link UserSignupResponse}
     */
    public UserSignupResponse signup(UserSignupRequest request) {
        userRepository
            .findByUsername(request.getUsername())
            .ifPresent(it -> {
                throw ApiException.of(SIGNUP_ERROR);
            });

        UserRecord user = new UserRecord();
        long uid = uidGenerator.nextUid();
        user.setId(uid);
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname("nn-" + request.getUsername());
        user.setGender((byte) 1);
        userRepository.save(user);
        return new UserSignupResponse();
    }

    /**
     * Get current uer by username.
     *
     * @param username username
     * @return {@link UserCurrentResponse}
     */
    public UserCurrentResponse getCurrent(String username) {
        return userRepository
            .findRecordByUsername(username)
            .map(record -> new UserCurrentResponse(protoMapper.mapping(record)))
            .orElse(new UserCurrentResponse());
    }

    /**
     * List users.
     *
     * @param pageable {@link Pageable}
     * @param request  {@link RpcUserListRequest}
     * @return {@link RpcUserListResponse}
     */
    public RpcUserListResponse listUsers(Pageable pageable, RpcUserListRequest request) {
        Long userId = request.getUserId();
        String nickname = request.getNickname();
        UserStatus status = UserStatus.fromValue(request.getStatus());
        List<Integer> genders = request.getGenders();
        Page<UserRecord> users =
            userRepository.listUsers(pageable, userId, nickname, status, genders);
        return new RpcUserListResponse()
            .setUsers(users.stream().map(protoMapper::mapping).collect(Collectors.toList()))
            .setPaging(generalProtoMapper.pagingPb(users));
    }

    /**
     * Get an user.
     *
     * @param request {@link RpcUserDataRequest}
     * @return {@link RpcUserDataResponse}
     */
    public RpcUserDataResponse getUser(RpcUserDataRequest request) {
        UserRecord userRecord = userRepository.findByUserId(request.getUserId())
            .orElseThrow(() -> ApiException.of(HttpStatus.NOT_FOUND, RpcErrorCode.ERROR));
        return new RpcUserDataResponse()
            .setUser(protoMapper.mapping(userRecord));
    }

    /**
     * Change user status.
     *
     * @param request {@link RpcUserChangeStatusRequest}
     * @return {@link RpcUserChangeStatusResponse}
     */
    public RpcUserChangeStatusResponse changeStatus(RpcUserChangeStatusRequest request) {
        UserStatus status = UserStatus.fromValue(request.getStatus());
        userRepository.changeStatus(request.getUserId(), status);
        return new RpcUserChangeStatusResponse().setStatus(status.getValue());
    }

    /**
     * Delete an user.
     *
     * @param request {@link RpcUserDeleteRequest}
     * @return {@link RpcUserDeleteResponse}
     */
    public RpcUserDeleteResponse delete(RpcUserDeleteRequest request) {
        userRepository.delete(request.getUserId());
        return new RpcUserDeleteResponse();
    }
}
