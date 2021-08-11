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

package tech.linqu.spring.cloud.starter.auth.domain;

import org.jooq.Converter;
import tech.linqu.spring.cloud.starter.utilities.enumeration.Enumeration;
import tech.linqu.spring.cloud.starter.utilities.enumeration.IntegerEnumMapper;

/**
 * User status enum.
 */
public enum UserStatus implements Enumeration {
    ACTIVE(0),
    SUSPENDED(1),
    INACTIVE(2);

    private final int value;

    private static final IntegerEnumMapper<UserStatus> mapper = new IntegerEnumMapper<>(values());

    public static UserStatus fromValue(Integer value) {
        return mapper.fromValue(value);
    }

    UserStatus(int value) {
        this.value = value;
    }

    /**
     * Get int value.
     *
     * @return value of the enum
     */
    @Override
    public int getValue() {
        return this.value;
    }

    /**
     * Converter fro db.
     */
    public static class DbConverter implements Converter<Byte, UserStatus> {

        /**
         * From {@link Byte} to {@link UserStatus}.
         *
         * @param databaseObject {@link Byte}
         * @return {@link UserStatus}
         */
        @Override
        public UserStatus from(Byte databaseObject) {
            return databaseObject == null ? null : UserStatus.fromValue(databaseObject.intValue());
        }

        /**
         * From {@link UserStatus} to {@link Byte}.
         *
         * @param userObject {@link UserStatus}
         * @return {@link Byte}
         */
        @Override
        public Byte to(UserStatus userObject) {
            return userObject == null ? null : (byte) userObject.value;
        }

        /**
         * Get from type.
         *
         * @return class of from type
         */
        @Override
        public Class<Byte> fromType() {
            return Byte.class;
        }

        /**
         * Get to type.
         *
         * @return class of to type
         */
        @Override
        public Class<UserStatus> toType() {
            return UserStatus.class;
        }
    }
}

