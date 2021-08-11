package tech.linqu.spring.cloud.starter.auth.tests.data;

import static tech.linqu.spring.cloud.starter.tests.TestDataConst.UID_1;
import static tech.linqu.spring.cloud.starter.tests.TestDataConst.UID_2;
import static tech.linqu.spring.cloud.starter.tests.TestDataConst.UID_3;

import tech.linqu.spring.cloud.starter.auth.domain.UserStatus;
import tech.linqu.spring.cloud.starter.auth.schema.tables.records.UserRecord;
import tech.linqu.spring.cloud.starter.tests.provider.RecordProvider;

/**
 * Test {@link UserRecord} data.
 */
public class DataUser {

    private static UserRecord createUserRecord(long uid) {
        UserRecord record = new UserRecord();
        record.setId(uid);
        record.setUsername("USER_" + uid);
        record.setPassword("PASSWORD_" + uid);
        record.setNickname("NICKNAME_" + uid);
        record.setGender((byte) 1);
        record.setAvatar("AVATAR_" + uid);
        record.setStatus(UserStatus.ACTIVE);
        record.setCreated(System.currentTimeMillis());
        return record;
    }

    /**
     * {@link RecordProvider} for {@link UserRecord}.
     */
    public static class User1 implements RecordProvider<UserRecord> {

        @Override
        public UserRecord get() {
            return createUserRecord(UID_1);
        }
    }

    /**
     * {@link RecordProvider} for {@link UserRecord}.
     */
    public static class User2 implements RecordProvider<UserRecord> {

        @Override
        public UserRecord get() {
            UserRecord record = createUserRecord(UID_2);
            record.setGender((byte) 2);
            record.setStatus(UserStatus.SUSPENDED);
            return record;
        }
    }

    /**
     * {@link RecordProvider} for {@link UserRecord}.
     */
    public static class User3 implements RecordProvider<UserRecord> {

        @Override
        public UserRecord get() {
            UserRecord record = createUserRecord(UID_3);
            record.setStatus(UserStatus.INACTIVE);
            return record;
        }
    }
}
