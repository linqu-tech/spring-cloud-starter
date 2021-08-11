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

package tech.linqu.spring.cloud.starter.auth.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.linqu.spring.cloud.starter.auth.service.UserService;
import tech.linqu.spring.cloud.starter.proto.external.user.UserCurrentRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserCurrentResponse;
import tech.linqu.spring.cloud.starter.proto.external.user.UserSignupRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserSignupResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserChangeStatusRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserChangeStatusResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDataRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDataResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDeleteRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserDeleteResponse;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserListRequest;
import tech.linqu.spring.cloud.starter.proto.internal.user.RpcUserListResponse;
import tech.linqu.webpb.runtime.mvc.WebpbRequestMapping;

/**
 * User controller.
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Signup.
     *
     * @param request {@link UserSignupRequest}
     * @return {@link UserSignupResponse}
     */
    @WebpbRequestMapping
    public UserSignupResponse signup(@Valid @RequestBody UserSignupRequest request) {
        return userService.signup(request);
    }

    /**
     * Get current logged user.
     *
     * @param authentication {@link Authentication}
     * @return {@link UserCurrentResponse}
     */
    @PreAuthorize("hasAuthority('SCOPE_ui')")
    @WebpbRequestMapping(message = UserCurrentRequest.class)
    public UserCurrentResponse getCurrent(Authentication authentication) {
        return userService.getCurrent(authentication.getName());
    }

    /**
     * List users.
     *
     * @param pageable {@link Pageable}
     * @param request  {@link RpcUserListRequest}
     * @return {@link RpcUserListResponse}
     */
    @PreAuthorize("hasAuthority('SCOPE_server')")
    @WebpbRequestMapping
    public RpcUserListResponse listUsers(Pageable pageable,
                                         @Valid @RequestBody RpcUserListRequest request) {
        return userService.listUsers(pageable, request);
    }

    /**
     * Get an user.
     *
     * @param request {@link RpcUserDataRequest}
     * @return {@link RpcUserDataResponse}
     */
    @PreAuthorize("hasAuthority('SCOPE_server')")
    @WebpbRequestMapping
    public RpcUserDataResponse getUser(@Valid RpcUserDataRequest request) {
        return userService.getUser(request);
    }

    /**
     * Change user status.
     *
     * @param request {@link RpcUserChangeStatusRequest}
     * @return {@link RpcUserChangeStatusResponse}
     */
    @PreAuthorize("hasAuthority('SCOPE_server')")
    @WebpbRequestMapping
    public RpcUserChangeStatusResponse changeStatus(
        @Valid @RequestBody RpcUserChangeStatusRequest request) {
        return userService.changeStatus(request);
    }

    /**
     * Delete an user.
     *
     * @param request {@link RpcUserDeleteRequest}
     * @return {@link RpcUserDeleteResponse}
     */
    @PreAuthorize("hasAuthority('SCOPE_server')")
    @WebpbRequestMapping
    public RpcUserDeleteResponse delete(@Valid @RequestBody RpcUserDeleteRequest request) {
        return userService.delete(request);
    }
}
