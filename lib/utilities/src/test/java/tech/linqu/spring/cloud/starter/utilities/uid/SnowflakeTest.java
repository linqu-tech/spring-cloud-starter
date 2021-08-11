package tech.linqu.spring.cloud.starter.utilities.uid;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class SnowflakeTest {

    private void setValue(Snowflake snowflake, String property, long value) {
        try {
            Field field = Snowflake.class.getDeclaredField(property);
            field.setAccessible(true);
            field.set(snowflake, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void givenInvalidConstructorArgs_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Snowflake(12345678L, 0));
        assertThrows(IllegalArgumentException.class, () -> new Snowflake(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Snowflake(0, 12345678L));
        assertThrows(IllegalArgumentException.class, () -> new Snowflake(0, -1));
    }

    @Test
    void givenClockMoveBackwards_whenGenerateId_shouldThrowException() {
        Snowflake snowflake = new Snowflake(0, 0);
        assertDoesNotThrow(snowflake::nextId);
        setValue(snowflake, "lastStamp", System.currentTimeMillis() + 1000);
        assertThrows(RuntimeException.class, snowflake::nextId);
    }

    @Test
    void givenInSameMilli_whenGenerateId_shouldBeClose() {
        Snowflake snowflake = spy(new Snowflake(0, 0));
        doReturn(1001L).when(snowflake).getMillis();
        long uid = snowflake.nextId();
        assertEquals(uid + 1, snowflake.nextId());
    }

    @Test
    void whenSequenceOverflow_shouldGenerateSuccess() {
        Snowflake snowflake = new Snowflake(0, 0);
        for (int i = 0; i < 32; i++) {
            setValue(snowflake, "sequence", 4095);
            assertDoesNotThrow(snowflake::nextId);
        }
    }
}
