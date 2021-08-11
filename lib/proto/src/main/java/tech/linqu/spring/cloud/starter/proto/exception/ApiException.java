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

package tech.linqu.spring.cloud.starter.proto.exception;

import org.springframework.http.HttpStatus;

/**
 * External API exception.
 */
public class ApiException extends AbstractApiException {

    private ApiException(HttpStatus status, ApiErrorCode error) {
        super(status, error);
    }

    /**
     * Create an exception.
     *
     * @param error {@link ApiErrorCode}
     * @return {@link AbstractApiException}
     */
    public static ApiException of(ApiErrorCode error) {
        return new ApiException(HttpStatus.BAD_REQUEST, error);
    }

    /**
     * Create an exception.
     *
     * @param status {@link HttpStatus}
     * @param error  {@link ApiErrorCode}
     * @return {@link AbstractApiException}
     */
    public static ApiException of(HttpStatus status, ApiErrorCode error) {
        return new ApiException(status, error);
    }

    /**
     * Not fill the stacktrace.
     *
     * @return {@link Throwable}
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
