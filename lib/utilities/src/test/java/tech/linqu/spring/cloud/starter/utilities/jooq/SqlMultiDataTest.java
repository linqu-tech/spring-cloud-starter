package tech.linqu.spring.cloud.starter.utilities.jooq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tech.linqu.spring.cloud.starter.tests.schema.Tables.TEST;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import tech.linqu.spring.cloud.starter.tests.schema.tables.records.TestRecord;

class SqlMultiDataTest {

    @Test
    void givenEmptyRecords_whenCreate_shouldReturnInstance() {
        assertNotNull(SqlMultiData.of(Collections.emptyList(), null));
        assertNotNull(SqlMultiData.of(null, null));
    }

    @Test
    void givenRecords_whenCreate_shouldReturnInstance() {
        TestRecord record = new TestRecord();
        record.setTest1(1L);
        record.setTest2("2");
        record.setTest3(3);
        SqlMultiData data = SqlMultiData.of(Collections.singletonList(record), null);
        assertEquals(TEST, data.getTable());
        assertEquals(3, data.getKeys().size());
        assertEquals(1, data.getValuesList().size());
        assertEquals(3, data.getFieldSize());
    }

    @Test
    void givenRecordsAndExcludes_whenCreate_shouldReturnInstance() {
        TestRecord record = new TestRecord();
        record.setTest1(1L);
        record.setTest2("2");
        record.setTest3(3);
        SqlMultiData data = SqlMultiData
            .of(Arrays.asList(record, record), Arrays.asList(TEST.TEST2, TEST.TEST3));
        assertEquals(1, data.getFieldSize());
    }
}
