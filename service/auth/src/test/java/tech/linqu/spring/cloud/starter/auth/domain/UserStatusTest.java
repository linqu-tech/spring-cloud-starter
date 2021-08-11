package tech.linqu.spring.cloud.starter.auth.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class UserStatusTest {

    @Test
    void shouldConverterFromValueSuccess() {
        UserStatus.DbConverter converter = new UserStatus.DbConverter();
        assertNull(converter.from(null));
        assertEquals(UserStatus.ACTIVE, converter.from((byte) 0));
    }

    @Test
    void shouldConverterToValueSuccess() {
        UserStatus.DbConverter converter = new UserStatus.DbConverter();
        assertNull(converter.to(null));
        assertEquals((byte) 0, converter.to(UserStatus.ACTIVE));
    }
}
