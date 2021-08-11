package tech.linqu.spring.cloud.starter.auth.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.linqu.spring.cloud.starter.auth.schema.tables.UserTable.USER;
import static tech.linqu.spring.cloud.starter.tests.TestDataConst.UID_1;
import static tech.linqu.spring.cloud.starter.tests.TestDataConst.UID_2;

import java.util.Collections;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tech.linqu.spring.cloud.starter.auth.domain.User;
import tech.linqu.spring.cloud.starter.auth.domain.UserStatus;
import tech.linqu.spring.cloud.starter.auth.schema.tables.records.UserRecord;
import tech.linqu.spring.cloud.starter.auth.tests.SchemaTablesProvider;
import tech.linqu.spring.cloud.starter.auth.tests.data.DataUser;
import tech.linqu.spring.cloud.starter.tests.annotation.DataProvider;
import tech.linqu.spring.cloud.starter.tests.annotation.TeardownTables;
import tech.linqu.spring.cloud.starter.tests.tests.SpringBootDataTests;

@TeardownTables(SchemaTablesProvider.class)
class UserRepositoryTest extends SpringBootDataTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DSLContext dsl;

    @DataProvider(value = { DataUser.User1.class })
    @Test
    void givenUserExists_whenFindByUsername_shouldGetUser() {
        Optional<User> optionalUser = userRepository.findByUsername("USER_10001");
        assertTrue(optionalUser.isPresent());
    }

    @Test
    void givenUserNotExists_whenFindByUsername_shouldGetEmpty() {
        Optional<User> optionalUser = userRepository.findByUsername("USER_10001");
        assertFalse(optionalUser.isPresent());
    }

    @DataProvider(value = { DataUser.User1.class })
    @Test
    void givenUserExists_whenFindRecordByUsername_shouldGetUserRecord() {
        Optional<UserRecord> optionalUser = userRepository.findRecordByUsername("USER_10001");
        assertTrue(optionalUser.isPresent());
    }

    @Test
    void givenUserNotExists_whenFindRecordByUsername_shouldGetEmpty() {
        Optional<UserRecord> optionalUser = userRepository.findRecordByUsername("USER_10001");
        assertFalse(optionalUser.isPresent());
    }

    @DataProvider(value = { DataUser.User1.class })
    @Test
    void givenUserExists_whenFindByUserId_shouldGetUserRecord() {
        Optional<UserRecord> optionalUser = userRepository.findByUserId(UID_1);
        assertTrue(optionalUser.isPresent());
    }

    @Test
    void givenUserNotExists_whenFindByUserId_shouldGetEmpty() {
        Optional<UserRecord> optionalUser = userRepository.findByUserId(UID_1);
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void givenUserNotExists_whenSave_shouldInsertSuccess() {
        userRepository.save(new DataUser.User1().get());
        assertTrue(dsl.fetchExists(USER, USER.ID.eq(UID_1)));
    }

    @DataProvider(value = { DataUser.User1.class })
    @Test
    void givenUserExists_whenSave_shouldThrowError() {
        UserRecord record = new DataUser.User1().get();
        record.setId(UID_2);
        assertThrows(Exception.class, () -> userRepository.save(record));
    }

    @DataProvider(value = { DataUser.User1.class, DataUser.User2.class, DataUser.User3.class })
    @Test
    void shouldListUsersSuccess() {
        Pageable pageable = PageRequest.of(1, 10);
        Page<UserRecord> records = userRepository.listUsers(pageable, null, null, null, null);
        assertEquals(3, records.getTotalElements());

        records = userRepository.listUsers(pageable, UID_1, null, null, null);
        assertEquals(1, records.getTotalElements());

        records = userRepository.listUsers(pageable, null, "NICKNAME_", null, null);
        assertEquals(3, records.getTotalElements());

        records = userRepository.listUsers(pageable, null, null, UserStatus.ACTIVE, null);
        assertEquals(1, records.getTotalElements());

        records =
            userRepository.listUsers(pageable, null, null, null, Collections.singletonList(1));
        assertEquals(2, records.getTotalElements());
    }

    @DataProvider(value = { DataUser.User1.class })
    @Test
    void givenUserExists_whenChangeStatus_shouldSuccess() {
        userRepository.changeStatus(UID_1, UserStatus.INACTIVE);
        Record1<UserStatus> data = dsl
            .select(USER.STATUS).from(USER).where(USER.ID.eq(UID_1)).fetchOne();
        assertNotNull(data);
        assertEquals(UserStatus.INACTIVE, data.get(0));
    }

    @Test
    void givenUserNotExists_whenChangeStatus_shouldNoException() {
        assertDoesNotThrow(() -> userRepository.changeStatus(UID_1, UserStatus.INACTIVE));
    }

    @DataProvider(value = { DataUser.User1.class })
    @Test
    void givenUserExists_whenDelete_shouldSuccess() {
        userRepository.delete(UID_1);
        assertFalse(dsl.fetchExists(USER, USER.ID.eq(UID_1)));
    }

    @Test
    void givenUserNotExists_whenDelete_shouldNoException() {
        assertDoesNotThrow(() -> userRepository.delete(UID_1));
    }
}
