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

import org.jooq.Condition;
import org.jooq.impl.DSL;

/**
 * Build jooq query condition.
 */
public class ConditionBuilder {

    private Condition condition;

    /**
     * Create a builder.
     *
     * @return {@link ConditionBuilder}
     */
    public static ConditionBuilder builder() {
        return new ConditionBuilder();
    }

    /**
     * And condition.
     *
     * @param other {@link Condition}
     * @return {@link ConditionBuilder}
     */
    public ConditionBuilder and(Condition other) {
        if (other != null) {
            condition = condition == null ? other : condition.and(other);
        }
        return this;
    }

    /**
     * Or condition.
     *
     * @param other {@link Condition}
     * @return {@link ConditionBuilder}
     */
    public ConditionBuilder or(Condition other) {
        if (other != null) {
            condition = condition == null ? other : condition.or(other);
        }
        return this;
    }

    /**
     * Build {@link Condition}.
     *
     * @return {@link Condition}
     */
    public Condition build() {
        return condition == null ? DSL.noCondition() : condition;
    }

    /**
     * Build a nullable {@link Condition}.
     *
     * @return {@link Condition}
     */
    public Condition nullable() {
        return condition;
    }
}
