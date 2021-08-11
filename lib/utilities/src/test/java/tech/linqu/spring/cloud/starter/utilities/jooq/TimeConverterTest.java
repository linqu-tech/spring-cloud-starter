package tech.linqu.spring.cloud.starter.utilities.jooq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TimeConverterTest {

    @Test
    void shouldFromTimeSuccess() {
        TimeConverter converter = new TimeConverter();
        assertNull(converter.from(null));
        assertEquals(946684800000L, converter.from(converter.to(946684800000L)));
    }

    @Test
    void shouldToTimeSuccess() {
        TimeConverter converter = new TimeConverter();
        assertNull(converter.to(null));
        LocalDateTime time = converter.to(946684800000L);
        assertEquals(946684800000L, converter.from(time));
    }

    @Test
    void shouldGetFromTypeSuccess() {
        TimeConverter converter = new TimeConverter();
        assertEquals(LocalDateTime.class, converter.fromType());
    }

    @Test
    void shouldToTypeSuccess() {
        TimeConverter converter = new TimeConverter();
        assertEquals(Long.class, converter.toType());
    }
}
