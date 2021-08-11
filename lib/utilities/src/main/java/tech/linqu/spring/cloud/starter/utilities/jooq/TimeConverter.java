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

package tech.linqu.spring.cloud.starter.utilities.jooq;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;
import org.jooq.Converter;

/**
 * Mapping between {@link LocalDateTime} and millis timestamp.
 */
public class TimeConverter implements Converter<LocalDateTime, Long> {

    private static final ZoneId zoneId = TimeZone.getDefault().toZoneId();

    /**
     * Mapping {@link LocalDateTime} to millis timestamp.
     *
     * @param localDateTime {@link LocalDateTime}
     * @return millis timestamp
     */
    @Override
    public Long from(LocalDateTime localDateTime) {
        return localDateTime == null ? null :
            localDateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    /**
     * Mapping millis timestamp to {@link LocalDateTime}.
     *
     * @param timestamp millis timestamp
     * @return {@link LocalDateTime}
     */
    @Override
    public LocalDateTime to(Long timestamp) {
        return timestamp == null ? null :
            LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId);
    }

    /**
     * From type class.
     *
     * @return class of {@link LocalDateTime}
     */
    @Override
    public Class<LocalDateTime> fromType() {
        return LocalDateTime.class;
    }

    /**
     * To type class.
     *
     * @return class of {@link Long}
     */
    @Override
    public Class<Long> toType() {
        return Long.class;
    }
}
