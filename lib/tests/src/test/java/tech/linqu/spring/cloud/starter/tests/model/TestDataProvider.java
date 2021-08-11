package tech.linqu.spring.cloud.starter.tests.model;

import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.Collections;
import org.jooq.TableRecord;
import tech.linqu.spring.cloud.starter.tests.provider.RecordProvider;
import tech.linqu.spring.cloud.starter.tests.provider.RecordsProvider;
import tech.linqu.spring.cloud.starter.tests.schema.tables.records.TestRecord;

/**
 * Class for test.
 */
public class TestDataProvider {

    /**
     * Class for test.
     */
    public static class Test1 implements RecordProvider<TestRecord> {

        @Override
        public TestRecord get() {
            return mock(TestRecord.class);
        }
    }

    /**
     * Class for test.
     */
    public static class Test2 implements RecordsProvider {

        @Override
        public Collection<TableRecord<?>> get() {
            return Collections.emptySet();
        }
    }

    /**
     * Class for test.
     */
    public abstract static class Test3 implements RecordsProvider {

    }
}
