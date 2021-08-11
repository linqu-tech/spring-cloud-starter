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

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Abstract API exception.
 */
@AllArgsConstructor
public abstract class AbstractApiException extends RuntimeException {

    /**
     * {@link HttpStatus}.
     */
    private final HttpStatus status;

    /**
     * Error code.
     */
    private final ApiErrorCode errorCode;

    /**
     * Not fill the stacktrace.
     *
     * @return {@link Throwable}
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    /**
     * Get {@link HttpStatus}.
     *
     * @return {@link HttpStatus}
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Get error code.
     *
     * @return error code
     */
    public ApiErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Get error value.
     *
     * @return error value
     */
    public int getErrorValue() {
        return errorCode.getValue();
    }
}
