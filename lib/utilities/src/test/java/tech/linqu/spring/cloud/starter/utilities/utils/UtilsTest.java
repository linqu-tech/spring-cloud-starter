package tech.linqu.spring.cloud.starter.utilities.utils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tech.linqu.spring.cloud.starter.utilities.utils.Utils.uncheckedCall;
import static tech.linqu.spring.cloud.starter.utilities.utils.Utils.uncheckedRun;

import org.junit.jupiter.api.Test;

class UtilsTest {

    @Test
    void shouldHideCallableExceptionSuccess() {
        assertEquals(0, uncheckedCall(() -> 0));
        assertThrows(RuntimeException.class, () -> uncheckedCall(() -> {
            throw new RuntimeException("Error");
        }), "Error");
        assertThrows(RuntimeException.class, () -> uncheckedCall(() -> {
            throw new IllegalAccessException("Error");
        }));
    }

    @Test
    void shouldHideRunnableExceptionSuccess() {
        assertDoesNotThrow(() -> uncheckedRun(() -> {
        }));
        assertThrows(RuntimeException.class, () -> uncheckedRun(() -> {
            throw new RuntimeException("Error");
        }), "Error");
    }
}
