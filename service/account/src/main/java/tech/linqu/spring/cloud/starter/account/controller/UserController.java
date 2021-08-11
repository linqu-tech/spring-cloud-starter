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

package tech.linqu.spring.cloud.starter.account.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.linqu.spring.cloud.starter.account.service.UserService;
import tech.linqu.spring.cloud.starter.proto.external.user.UserChangeStatusRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserChangeStatusResponse;
import tech.linqu.spring.cloud.starter.proto.external.user.UserDataRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserDataResponse;
import tech.linqu.spring.cloud.starter.proto.external.user.UserDeleteRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserDeleteResponse;
import tech.linqu.spring.cloud.starter.proto.external.user.UserListRequest;
import tech.linqu.spring.cloud.starter.proto.external.user.UserListResponse;
import tech.linqu.webpb.runtime.mvc.WebpbRequestMapping;

/**
 * User controller.
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * List users.
     *
     * @param request {@link UserListRequest}
     * @return {@link UserListResponse}
     */
    @PreAuthorize("hasAuthority('SCOPE_ui')")
    @WebpbRequestMapping
    public UserListResponse listUsers(@Valid @RequestBody UserListRequest request) {
        return userService.listUsers(request);
    }

    /**
     * Get an user.
     *
     * @param request {@link UserDataRequest}
     * @return {@link UserDataResponse}
     */
    @PreAuthorize("hasAuthority('SCOPE_ui')")
    @WebpbRequestMapping
    public UserDataResponse getUser(@Valid UserDataRequest request) {
        return userService.getUser(request);
    }

    /**
     * Change user status.
     *
     * @param request {@link UserChangeStatusRequest}
     * @return {@link UserChangeStatusResponse}
     */
    @PreAuthorize("hasAuthority('SCOPE_ui')")
    @WebpbRequestMapping
    public UserChangeStatusResponse changeStatus(
        @Valid @RequestBody UserChangeStatusRequest request) {
        return userService.changeStatus(request);
    }

    /**
     * Delete an user.
     *
     * @param request {@link UserDeleteRequest}
     * @return {@link UserDeleteResponse}
     */
    @PreAuthorize("hasAuthority('SCOPE_ui')")
    @WebpbRequestMapping
    public UserDeleteResponse delete(@Valid @RequestBody UserDeleteRequest request) {
        return userService.delete(request);
    }
}
