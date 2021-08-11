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

package tech.linqu.spring.cloud.starter.auth.repository;

import static tech.linqu.spring.cloud.starter.auth.schema.tables.UserTable.USER;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tech.linqu.spring.cloud.starter.auth.domain.User;
import tech.linqu.spring.cloud.starter.auth.domain.UserStatus;
import tech.linqu.spring.cloud.starter.auth.schema.tables.records.UserRecord;
import tech.linqu.spring.cloud.starter.utilities.jooq.ConditionBuilder;
import tech.linqu.spring.cloud.starter.utilities.jooq.PageableQuery;

/**
 * User repository.
 */
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DSLContext dsl;

    /**
     * Find an user by username.
     *
     * @param username username
     * @return optional {@link User}
     */
    public Optional<User> findByUsername(String username) {
        return dsl
            .selectFrom(USER)
            .where(USER.USERNAME.eq(username))
            .fetchOptionalInto(User.class);
    }

    /**
     * Find an {@link UserRecord}.
     *
     * @param username username
     * @return optional of {@link UserRecord}
     */
    public Optional<UserRecord> findRecordByUsername(String username) {
        return dsl
            .selectFrom(USER)
            .where(USER.USERNAME.eq(username))
            .fetchOptionalInto(UserRecord.class);
    }

    /**
     * Find an user by Id.
     *
     * @param userId userId
     * @return optional {@link UserRecord}
     */
    public Optional<UserRecord> findByUserId(Long userId) {
        return dsl
            .selectFrom(USER)
            .where(USER.ID.eq(userId))
            .fetchOptionalInto(UserRecord.class);
    }

    /**
     * Save an user.
     *
     * @param record {@link UserRecord}
     */
    public void save(UserRecord record) {
        dsl
            .insertInto(USER)
            .set(record)
            .execute();
    }

    /**
     * List users.
     *
     * @param pageable {@link Pageable}
     * @param userId   userId
     * @param nickname nickname
     * @param status   {@link UserStatus}
     * @return paget of {@link UserRecord}
     */
    public Page<UserRecord> listUsers(Pageable pageable, Long userId, String nickname,
                                      UserStatus status, List<Integer> genders) {
        Condition condition = ConditionBuilder.builder()
            .and(userId != null ? USER.ID.eq(userId) : null)
            .and(StringUtils.isNotEmpty(nickname) ? USER.NICKNAME.contains(nickname) : null)
            .and(status != null ? USER.STATUS.eq(status) : null)
            .and(CollectionUtils.isEmpty(genders) ? null : USER.GENDER.in(genders))
            .build();
        return PageableQuery.of(
            USER, pageable,
            dsl.selectFrom(USER).where(condition),
            dsl.selectCount().from(USER).where(condition)
        ).fetch();
    }

    /**
     * Changet user status.
     *
     * @param userId userId
     * @param status status
     */
    public void changeStatus(Long userId, UserStatus status) {
        dsl
            .update(USER)
            .set(USER.STATUS, status)
            .where(USER.ID.eq(userId))
            .execute();
    }

    /**
     * Delete an user.
     *
     * @param userId userId
     */
    public void delete(Long userId) {
        dsl
            .deleteFrom(USER)
            .where(USER.ID.eq(userId))
            .execute();
    }
}
