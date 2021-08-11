package tech.linqu.spring.cloud.starter.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.util.Set;
import org.jooq.Table;
import org.junit.jupiter.api.Test;
import tech.linqu.spring.cloud.starter.tests.model.TestTables;

class TestDataUtilsTest {

    @Test
    void shouldResolveTablesSuccess() {
        Set<Table<?>> tables = TestDataUtils.resolveTables(TestTables.class);
        assertEquals(1, tables.size());
    }

    @Test
    void givenFieldNotAccessible_whenGetField_shouldThrowError() throws NoSuchFieldException {
        Field field = TestTables.class.getDeclaredField("test1");
        assertThrows(RuntimeException.class, () -> TestDataUtils.getField(field, null));
    }
}
