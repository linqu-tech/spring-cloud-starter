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

package tech.linqu.spring.cloud.starter.utilities.mapstruct;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mapstruct.Named;
import org.mapstruct.TargetType;
import tech.linqu.spring.cloud.starter.utilities.enumeration.Enumeration;

/**
 * General mapstruct mapper.
 */
@RequiredArgsConstructor
public class GeneralMapper {

    /**
     * Enum to integer mapper.
     */
    public static final String ENUM_TO_INTEGER = "ENUM_TO_INTEGER";

    /**
     * Integer to enum mapper.
     */
    public static final String INTEGER_TO_ENUM = "INTEGER_TO_ENUM";

    /**
     * Value to enum mapper.
     *
     * @param value integer value
     * @param type  target enum type
     * @param <T>   target type
     * @return enum
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Named(INTEGER_TO_ENUM)
    public <T extends Enumeration> T integerToEnum(Integer value, @TargetType Class<T> type) {
        Method method = type.getDeclaredMethod("fromValue", Integer.class);
        return (T) method.invoke(null, value);
    }

    /**
     * Enum to value mapper.
     *
     * @param enumeration {@link Enumeration}
     * @return value
     */
    @Named(ENUM_TO_INTEGER)
    public static Integer enumToInteger(Enumeration enumeration) {
        return enumeration == null ? null : enumeration.getValue();
    }
}
